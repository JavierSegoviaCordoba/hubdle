import com.javiersc.gradle.extensions.version.catalogs.artifact
import com.javiersc.gradle.extensions.version.catalogs.getLibraries
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.gradle.version.isSnapshot
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import io.gitlab.arturbosch.detekt.Detekt
import java.util.Locale
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String? =
    getStringProperty("kotlinVersion").orNull.takeIf(String?::isNotNullNorBlank)
val semverTagPrefix: String =
    getStringProperty("semver.tagPrefix").orNull.takeIf { it != "null" } ?: ""

hubdle {
    config {
        analysis()
        coverage()
        documentation { //
            api()
        }
        explicitApi()
        languageSettings { //
            experimentalStdlibApi()
        }
        projectConfig()
        publishing()
        versioning {
            semver { //
                val hasSameTagPrefix: Boolean = semverTagPrefix == tagPrefix.get()
                if (kotlinVersion.isNotNullNorBlank() && hasSameTagPrefix) {
                    mapVersion { gradleVersion ->
                        gradleVersion.mapIfKotlinVersionIsProvided(kotlinVersion)
                    }
                }
            }
        }
        testing {
            test { //
                systemProperties["KOTLIN_VERSION"] = kotlinVersion.orEmpty()
            }
        }
    }

    kotlin {
        jvm {
            features {
                jvmVersion(JavaVersion.VERSION_11)

                gradle {
                    plugin {
                        gradlePlugin {
                            plugins {
                                create("hubdle") {
                                    id = "com.javiersc.hubdle"
                                    displayName = "Hubdle"
                                    description = "Easy setup for projects or settings"
                                    implementationClass = "com.javiersc.hubdle.HubdlePlugin"
                                    tags.set(listOf("hubdle"))
                                }
                                create("hubdle project") {
                                    id = "com.javiersc.hubdle.project"
                                    displayName = "Hubdle project"
                                    description = "Easy setup for each kind of project"
                                    implementationClass =
                                        "com.javiersc.hubdle.project.HubdleProjectPlugin"
                                    tags.set(listOf("hubdle project"))
                                }
                                create("hubdle settings") {
                                    id = "com.javiersc.hubdle.settings"
                                    displayName = "Hubdle settings"
                                    description = "Easy settings setup"
                                    implementationClass =
                                        "com.javiersc.hubdle.settings.HubdleSettingsPlugin"
                                    tags.set(listOf("hubdle settings"))
                                }
                            }
                        }

                        pluginUnderTestExternalDependencies(
                            hubdle.android.toolsBuild.gradle,
                            hubdle.jetbrains.kotlin.kotlinGradlePlugin,
                        )
                    }
                }
            }

            main {
                dependencies {
                    api(hubdle.adarshr.gradleTestLoggerPlugin)
                    api(hubdle.android.toolsBuild.gradle)
                    api(hubdle.diffplug.spotless.spotlessPluginGradle)
                    api(hubdle.github.gradleNexus.publishPlugin)
                    api(hubdle.gitlab.arturboschDetekt.detektGradlePlugin)
                    api(hubdle.gradle.publish.pluginPublishPlugin)
                    api(hubdle.gradle.testRetryGradlePlugin)
                    api(hubdle.jetbrains.dokka.dokkaGradlePlugin)
                    api(hubdle.jetbrains.intellijPlugins.gradleChangelogPlugin)
                    api(hubdle.jetbrains.intellijPlugins.gradleIntellijPlugin)
                    api(hubdle.jetbrains.kotlin.kotlinGradlePlugin)
                    api(hubdle.jetbrains.kotlinx.binaryCompatibilityValidator)
                    api(hubdle.jetbrains.kotlinx.serialization)
                    api(hubdle.vyarus.gradleMkdocsPlugin)

                    api(hubdle.plugins.cash.molecule.artifact)
                    api(hubdle.plugins.cash.sqldelight.artifact)
                    api(hubdle.plugins.gradle.enterprise.artifact)
                    api(hubdle.plugins.javiersc.semver.artifact)
                    api(hubdle.plugins.jetbrains.compose.artifact)
                    api(hubdle.plugins.jetbrains.kotlinx.kover.artifact)
                    api(hubdle.plugins.sonarqube.artifact)

                    compileOnly(hubdle.jetbrains.kotlin.kotlinCompilerInternalTestFramework)

                    implementation(hubdle.eclipse.jgit)
                }

                resources.srcDirs(file(rootDir.resolve("gradle/hubdle")))
            }

            testFixtures()
            testFunctional()
            testIntegration()
        }
    }
}

fun GradleVersion.mapIfKotlinVersionIsProvided(kotlinVersion: String): String {
    val major: Int = major
    val minor: Int = minor
    val patch: Int = patch
    check(kotlinVersion.isKotlinDevVersion()) {
        """ |Kotlin version: $kotlinVersion
            |Requirements to use a specific Kotlin version:  
            | - It must be a dev version, for example: `1.9.20-dev-5788`
            |Check the Kotlin dev versions on the bootstrap repository:
            |  - https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap/org/jetbrains/kotlin/ 
        """
            .trimMargin()
    }
    check(isSnapshot) {
        """ |Current version: ${this@mapIfKotlinVersionIsProvided}
            |Kotlin version: $kotlinVersion
            |Requirements to use a specific Kotlin version:  
            | - Use a SNAPSHOT version with `-P semver.stage=snapshot`
            | - Clean repo or use `-P semver.checkClean=false`
        """
            .trimMargin()
    }
    return "$major.$minor.$patch+$kotlinVersion-SNAPSHOT"
}

fun String.isKotlinDevVersion(): Boolean =
    matches(Regex("""(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)-dev-(0|[1-9]\d*)"""))

generateHubdle()

val Project.buildDirectory: File
    get() = layout.buildDirectory.get().asFile

fun Project.generateHubdle() {
    val hubdleCodegen: TaskCollection<Task> = tasks.maybeRegisterLazily("generateHubdle")
    tasks.withType<Jar>().configureEach { mustRunAfter(hubdleCodegen) }
    tasks.withType<Detekt>().configureEach { mustRunAfter(hubdleCodegen) }

    the<KotlinProjectExtension>()
        .sourceSets["main"]
        .kotlin
        .srcDirs(buildDirectory.resolve("generated/main/kotlin"))

    hubdleCodegen.configureEach {
        group = "build"

        inputs.files(
            rootDir.resolve("gradle/hubdle.libs.versions.toml"),
            rootDir.resolve("gradle/libs.versions.toml"),
        )

        outputs.dir(buildDirectory.resolve(generatedDependenciesInternalDir))

        doLast {
            buildConstants()
            buildHubdleDependencies()
        }
    }

    tasks.namedLazily<Task>("apiCheck").configureEach { dependsOn(hubdleCodegen) }

    tasks.namedLazily<Task>("apiDump").configureEach { dependsOn(hubdleCodegen) }

    tasks.namedLazily<Task>(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configureEach {
        dependsOn(hubdleCodegen)
    }

    tasks.withType<JavaCompile>().configureEach { dependsOn(hubdleCodegen) }

    tasks.withType<KotlinCompile>().configureEach { dependsOn(hubdleCodegen) }
}

val generatedDependenciesInternalDir =
    "generated/main/kotlin/com/javiersc/hubdle/project/extensions/dependencies/_internal"

fun Project.buildConstants() {
    catalogDependencies.forEach { minimalDependency ->
        val fileName = minimalDependency.module.toString().replace(":", "_")
        val dependencyVariableName = fileName.buildDependencyVariableName()
        val dependencyVersion = minimalDependency.versionConstraint.displayName
        buildDirectory
            .resolve(generatedDependenciesInternalDir)
            .resolve("constants/$fileName.kt")
            .apply {
                parentFile.mkdirs()
                createNewFile()
                writeText(
                    """
                        |package com.javiersc.hubdle.project.extensions.dependencies._internal.constants
                        |
                        |internal const val ${dependencyVariableName}_LIBRARY: String =
                        |    "${minimalDependency.module}:$dependencyVersion"
                        |
                        |internal const val ${dependencyVariableName}_MODULE: String =
                        |    "${minimalDependency.module}"
                        |
                        |
                    """
                        .trimMargin(),
                )
            }
    }
}

fun Project.buildHubdleDependencies() {
    layout.buildDirectory
        .get()
        .asFile
        .resolve(generatedDependenciesInternalDir)
        .resolve("constants/HUBDLE_ALIASES.kt")
        .apply {
            parentFile.mkdirs()
            createNewFile()
            val libraryAliases: String =
                catalog.libraryAliases.joinToString("\n") { alias ->
                    """|internal const val ${alias.sanitizeAlias()} = "$alias""""
                }
            val pluginAliases: String =
                catalog.pluginAliases.joinToString("\n") { alias ->
                    """|internal const val ${alias.sanitizeAlias()}_plugin = "$alias""""
                }
            val content =
                """ |package com.javiersc.hubdle.project.extensions.dependencies._internal.aliases
                    |
                    $libraryAliases
                    |
                    $pluginAliases
                    |
                """
                    .trimMargin()
            writeText(content)
        }
}

fun String.buildDependencyVariableName(): String =
    replace(".", "_").replace("-", "_").uppercase(Locale.getDefault())

fun String.sanitizeAlias() = replace(".", "_")

val Project.catalog: VersionCatalog
    get() = the<VersionCatalogsExtension>().find("hubdle").get()

val Project.catalogDependencies: List<MinimalExternalModuleDependency>
    get() = the<VersionCatalogsExtension>().getLibraries(catalog)
