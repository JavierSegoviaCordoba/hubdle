import com.javiersc.gradle.extensions.version.catalogs.getLibraries
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import java.util.Locale
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

hubdle {
    config {
        analysis()
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
                    api(hubdle.javiersc.semver.semverGradlePlugin)
                    api(hubdle.jetbrains.compose.composeGradlePlugin)
                    api(hubdle.jetbrains.dokka.dokkaGradlePlugin)
                    api(hubdle.jetbrains.intellijPlugins.gradleChangelogPlugin)
                    api(hubdle.jetbrains.intellijPlugins.gradleIntellijPlugin)
                    api(hubdle.jetbrains.kotlin.kotlinGradlePlugin)
                    api(hubdle.jetbrains.kotlinx.binaryCompatibilityValidator)
                    api(hubdle.jetbrains.kotlinx.kover)
                    api(hubdle.jetbrains.kotlinx.serialization)
                    api(hubdle.sonarqube.scannerGradle.sonarqubeGradlePlugin)
                    api(hubdle.vyarus.gradleMkdocsPlugin)

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
            buildHubdleCatalog()
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
    val sqldelightVersion: String? = hubdle.cash.sqldelight.gradlePlugin.get().version
    buildDir
        .resolve(generatedDependenciesInternalDir)
        .resolve("constants/SQLDELIGHT_VERSION.kt")
        .apply {
            parentFile.mkdirs()
            createNewFile()
        }
        .writeText(
            """
                |package com.javiersc.hubdle.project.extensions.dependencies._internal.constants
                |
                |internal const val SQLDELIGHT_VERSION: String = "$sqldelightVersion"
                |
            """
                .trimMargin()
        )

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
                    |internal const val ${dependencyVariableName}_VERSION: String =
                    |    "$dependencyVersion"
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
            val aliases =
                catalog.libraryAliases.joinToString("\n") { alias ->
                    """|internal const val ${alias.sanitizeAlias()} = "$alias""""
                }
            val content =
                """ |package com.javiersc.hubdle.project.extensions.dependencies._internal.aliases
                    |
                    $aliases
                    |
                """
                    .trimMargin()
            writeText(content)
        }
}

fun Project.buildHubdleCatalog() {
    val aliases: List<String> = catalog.libraryAliases
    val aliasToLibraryMap: Map<String, String> =
        aliases.associateWith { alias ->
            val dependency: MinimalExternalModuleDependency = catalog.findLibrary(alias).get().get()
            val version: String? = dependency.version
            if (version != null) "${dependency.module}:${version}" else "${dependency.module}"
        }

    val builders =
        aliasToLibraryMap.map { (alias, library) -> """builder.library("$alias", "$library")""" }

    val indent = " ".repeat(12)

    val hubdleCatalog =
        """ |package com.javiersc.hubdle.project.extensions.dependencies._internal.catalog
            |
            |import org.gradle.api.initialization.Settings
            |
            |internal fun Settings.createHubdleCatalog() {
            |    dependencyResolutionManagement { management ->
            |        management.versionCatalogs { catalogs ->
            |            catalogs.create("hubdle") { builder -> //
            ${builders.joinToString("\n") { builder -> "|${builder.prependIndent(indent)}"}}
            |            }
            |        }
            |    }
            |}
            |
        """
            .trimMargin()

    buildDir.resolve(generatedDependenciesInternalDir).resolve("catalog/HubdleCatalog.kt").apply {
        parentFile.mkdirs()
        createNewFile()
        writeText(hubdleCatalog)
    }

    val sanitizedLibraries =
        aliasToLibraryMap
            .map { (alias, library) -> """|        "${alias.replace(".", "-")}" to "$library", """ }
            .joinToString("\n")

    val hubdleCatalogMap =
        """ |package com.javiersc.hubdle.project.extensions.dependencies._internal.catalog
            |
            |internal val hubdleAliasToLibraries =
            |    mapOf(
            $sanitizedLibraries
            |    )
            |
        """
            .trimMargin()

    buildDir
        .resolve(generatedDependenciesInternalDir)
        .resolve("catalog/HubdleCatalogMap.kt")
        .apply {
            parentFile.mkdirs()
            createNewFile()
            writeText(hubdleCatalogMap)
        }
}

fun String.buildDependencyVariableName(): String =
    replace(".", "_").replace("-", "_").uppercase(Locale.getDefault())

fun String.sanitizeAlias() = replace(".", "_")

val Project.catalog: VersionCatalog
    get() = the<VersionCatalogsExtension>().find("hubdle").get()

val Project.catalogDependencies: List<MinimalExternalModuleDependency>
    get() = the<VersionCatalogsExtension>().getLibraries(catalog)
