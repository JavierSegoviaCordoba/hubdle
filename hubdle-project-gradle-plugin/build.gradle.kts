import com.javiersc.gradle.extensions.version.catalogs.getLibraries
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.kotlin.stdlib.endWithNewLine
import com.javiersc.kotlin.stdlib.removeDuplicateEmptyLines
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.javiersc.hubdle)
}

hubdle {
    config {
        explicitApi()
        languageSettings { experimentalStdlibApi() }
        publishing()
    }

    kotlin {
        gradle {
            plugin {
                jvmVersion = 11

                tags("hubdle")

                gradlePlugin {
                    plugins {
                        create("hubdle project") {
                            id = "com.javiersc.hubdle.project"
                            displayName = "Hubdle project"
                            description = "Easy setup for each kind of project"
                            implementationClass = "com.javiersc.hubdle.project.HubdleProjectPlugin"
                        }
                    }
                }
                main {
                    dependencies {
                        api(libs.adarshr.gradleTestLoggerPlugin)
                        api(libs.android.toolsBuild.gradle)
                        api(libs.cash.molecule.gradlePlugin)
                        api(libs.cash.sqldelight.gradlePlugin)
                        api(libs.diffplug.spotless.spotlessPluginGradle)
                        api(libs.github.gradleNexus.publishPlugin)
                        api(libs.gitlab.arturboschDetekt.detektGradlePlugin)
                        api(libs.gradle.publish.pluginPublishPlugin)
                        api(libs.javiersc.semver.semverGradlePlugin)
                        api(libs.jetbrains.compose.composeGradlePlugin)
                        api(libs.jetbrains.dokka.dokkaGradlePlugin)
                        api(libs.jetbrains.intellijPlugins.gradleChangelogPlugin)
                        api(libs.jetbrains.intellijPlugins.gradleIntellijPlugin)
                        api(libs.jetbrains.kotlin.kotlinGradlePlugin)
                        api(libs.jetbrains.kotlinx.binaryCompatibilityValidator)
                        api(libs.jetbrains.kotlinx.kover)
                        api(libs.jetbrains.kotlinx.serialization)
                        api(libs.sonarqube.scannerGradle.sonarqubeGradlePlugin)
                        api(libs.vyarus.gradleMkdocsPlugin)

                        implementation(libs.eclipse.jgit)
                        implementation(libs.javiersc.semver.semverCore)
                    }
                }
                pluginUnderTestDependencies(
                    libs.android.toolsBuild.gradle,
                    libs.jetbrains.kotlin.kotlinGradlePlugin,
                )
            }
        }
    }
}

generateHubdleDependencies()

fun Project.generateHubdleDependencies() {
    val dependenciesCodegen: TaskCollection<Task> =
        tasks.maybeRegisterLazily("generateHubdleDependencies")

    the<KotlinProjectExtension>()
        .sourceSets["main"]
        .kotlin
        .srcDirs(buildDir.resolve("generated/main/kotlin"))

    dependenciesCodegen.configureEach {
        group = "build"

        doLast {
            buildConstants()

            buildHubdleDependenciesList()

            buildHubdleDependencies()
        }
    }

    tasks.namedLazily<Task>("apiCheck").configureEach { dependsOn(dependenciesCodegen) }

    tasks.namedLazily<Task>("apiDump").configureEach { dependsOn(dependenciesCodegen) }

    tasks.namedLazily<Task>(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configureEach {
        dependsOn(dependenciesCodegen)
    }

    tasks.withType<JavaCompile>().configureEach { dependsOn(dependenciesCodegen) }

    tasks.withType<KotlinCompile>().configureEach { dependsOn(dependenciesCodegen) }
}

val generatedDependenciesInternalDir =
    "generated/main/kotlin/com/javiersc/hubdle/extensions/dependencies/_internal"

fun Project.buildConstants() {
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
                |internal const val SQLDELIGHT_VERSION: String = "${libs.versions.sqldelight.get()}"
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

fun Project.buildHubdleDependenciesList() {
    buildDir
        .resolve(generatedDependenciesInternalDir)
        .resolve("hubdle_dependencies_list.kt")
        .apply {
            parentFile.mkdirs()
            createNewFile()
            val dependenciesAsStringList =
                catalogDependencies
                    .map {
                        val displayName = it.versionConstraint.displayName
                        val version = if (displayName.isBlank()) "" else ":$displayName"
                        """"${it.module}$version",""".prependIndent("        ")
                    }
                    .sorted()
            writeText(
                """
                    |package com.javiersc.hubdle.project.extensions.dependencies._internal
                    |
                    |internal val hubdleDependencies: List<String> =
                    |    listOf(
                    ${dependenciesAsStringList.joinToString("\n") { "|$it" }}
                    |    )
                    |
                """
                    .trimMargin(),
            )
        }
}

fun Project.buildHubdleDependencies() {
    buildDir.resolve("generated/main/kotlin/hubdle_dependencies.kt").apply {
        parentFile.mkdirs()
        createNewFile()
        writeText("")
        catalog.libraryAliases
            .map { libraryAlias -> catalog.findLibrary(libraryAlias).get().get() }
            .sortedBy { library -> library.module.toString() }
            .toSet()
            .forEach { library: MinimalExternalModuleDependency ->
                val dependencyVariableNames =
                    with(library) {
                            val fileName = module.toString().replace(":", "_")
                            val dependencyVariableName = fileName.buildDependencyVariableName()

                            val groupSanitized =
                                if (hasCommonEndingDomain) {
                                    val group =
                                        when {
                                            onlyDomain && endAndStartWithSameName -> {
                                                ""
                                            }
                                            endAndStartWithSameName -> {
                                                module.group.substringBeforeLast(".")
                                            }
                                            else -> module.group
                                        }

                                    group.substringAfter(".").groupOrNameSanitized()
                                } else {
                                    val group =
                                        if (endAndStartWithSameName) {
                                            module.group.substringBeforeLast(".")
                                        } else {
                                            module.group
                                        }
                                    group.groupOrNameSanitized()
                                }

                            val nameSanitized = module.name.groupOrNameSanitized().capitalized()
                            val dependencyName =
                                "$groupSanitized$nameSanitized"
                                    .sanitizeDependencyVariableName()
                                    .decapitalize()
                            """
                                |@HubdleDslMarker
                                |public fun Project.$dependencyName(): Provider<MinimalExternalModuleDependency> =
                                |    catalogDependency(${dependencyVariableName}_MODULE)
                            """
                        }
                        .lines()

                writeText(
                    readText() +
                        """ ${dependencyVariableNames.joinToString("\n")}
                            |
                        """
                            .trimMargin()
                )
            }
        writeText(
            """
                    |import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.*
                    |import com.javiersc.hubdle.project.extensions.HubdleDslMarker
                    |import com.javiersc.hubdle.project.extensions._internal.catalogDependency
                    |import org.gradle.api.artifacts.MinimalExternalModuleDependency
                    |import org.gradle.api.Project
                    |import org.gradle.api.provider.Provider
                    |
                    |
                """
                .trimMargin() + readText().removeDuplicateEmptyLines().endWithNewLine()
        )
    }
}

fun String.groupOrNameSanitized(): String =
    fold("") { acc: String, c: Char ->
        if (acc.lastOrNull() == '-' || acc.lastOrNull() == '.') {
            acc.dropLast(1) + c.toUpperCase()
        } else {
            acc + c
        }
    }

fun String.buildDependencyVariableName(): String =
    replace(".", "_").replace("-", "_").toUpperCase(java.util.Locale.getDefault())

fun String.sanitizeDependencyVariableName(): String {
    val words = this.map { if (it.isUpperCase()) "_$it" else it }.joinToString("").split("_")

    fun word(index: Int): String? = words.getOrNull(index)

    val newWords =
        if (word(0)?.capitalize() + word(1) == word(2) + word(3)) {
            words.subList(2, words.size)
        } else words
    return newWords.joinToString("")
}

val MinimalExternalModuleDependency.hasCommonEndingDomain: Boolean
    get() = module.group.split(".").first().count() <= 3

val MinimalExternalModuleDependency.endAndStartWithSameName: Boolean
    get() = module.name.startsWith(module.group.substringAfterLast("."))

val MinimalExternalModuleDependency.onlyDomain: Boolean
    get() = module.group.split(".").count() == 2

val Project.catalog: VersionCatalog
    get() = the<VersionCatalogsExtension>().find("hubdleLibs").get()

val Project.catalogDependencies: List<MinimalExternalModuleDependency>
    get() = the<VersionCatalogsExtension>().getLibraries(catalog)
