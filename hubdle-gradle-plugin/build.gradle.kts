import com.javiersc.gradle.extensions.version.catalogs.artifact
import com.javiersc.gradle.extensions.version.catalogs.getLibraries
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import java.util.Locale
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
        publishing()
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

                        pluginUnderTestDependencies(
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
                    api(hubdle.cash.molecule.gradlePlugin)
                    api(hubdle.cash.sqldelight.gradlePlugin)
                    api(hubdle.diffplug.spotless.spotlessPluginGradle)
                    api(hubdle.github.gradleNexus.publishPlugin)
                    api(hubdle.gitlab.arturboschDetekt.detektGradlePlugin)
                    api(hubdle.gradle.enterprise.comGradleEnterpriseGradlePlugin)
                    api(hubdle.gradle.publish.pluginPublishPlugin)
                    api(hubdle.gradle.testRetryGradlePlugin)
                    api(hubdle.javiersc.semver.semverGradlePlugin)
                    api(hubdle.jetbrains.compose.composeGradlePlugin)
                    api(hubdle.jetbrains.dokka.dokkaGradlePlugin)
                    api(hubdle.jetbrains.intellijPlugins.gradleChangelogPlugin)
                    api(hubdle.jetbrains.intellijPlugins.gradleIntellijPlugin)
                    api(hubdle.jetbrains.kotlin.kotlinGradlePlugin)
                    api(hubdle.jetbrains.kotlinx.binaryCompatibilityValidator)
                    api(hubdle.jetbrains.kotlinx.serialization)
                    api(hubdle.sonarqube.scannerGradle.sonarqubeGradlePlugin)
                    api(hubdle.vyarus.gradleMkdocsPlugin)

                    api(hubdle.plugins.jetbrains.kotlinx.kover.artifact)

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

generateHubdle()

fun Project.generateHubdle() {
    val hubdleCodegen: TaskCollection<Task> = tasks.maybeRegisterLazily("generateHubdle")
    tasks.named("sourcesJar").configure { mustRunAfter(hubdleCodegen) }
    tasks.named("detekt").configure { mustRunAfter(hubdleCodegen) }

    the<KotlinProjectExtension>()
        .sourceSets["main"]
        .kotlin
        .srcDirs(buildDir.resolve("generated/main/kotlin"))

    hubdleCodegen.configureEach {
        group = "build"

        inputs.files(
            rootDir.resolve("gradle/hubdle.libs.versions.toml"),
            rootDir.resolve("gradle/libs.versions.toml"),
        )

        outputs.dir(
            buildDir.resolve(generatedDependenciesInternalDir),
        )

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
        buildDir.resolve(generatedDependenciesInternalDir).resolve("constants/$fileName.kt").apply {
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
    buildDir
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
