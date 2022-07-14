import com.javiersc.gradle.extensions.version.catalogs.getLibraries
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.kotlin.stdlib.endWithNewLine
import com.javiersc.kotlin.stdlib.removeDuplicateEmptyLines
import java.util.Locale
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val dependenciesCodegen: TaskCollection<Task> =
    tasks.maybeRegisterLazily<Task>("dependenciesCodegen")

the<KotlinProjectExtension>()
    .sourceSets["main"]
    .kotlin
    .srcDirs(buildDir.resolve("generated/main/kotlin"))

val catalog: VersionCatalog = the<VersionCatalogsExtension>().find("hubdleLibs").get()

val dependencies: List<MinimalExternalModuleDependency> =
    the<VersionCatalogsExtension>().getLibraries(catalog)

dependenciesCodegen.configureEach {
    group = "build"

    doLast {
        buildConstants()

        buildHubdleDependenciesList()

        buildHubdleDependencies()
    }
}

tasks.namedLazily<Task>("apiCheck").configureEach {
    dependsOn(dependenciesCodegen)
}

tasks.namedLazily<Task>("apiDump").configureEach {
    dependsOn(dependenciesCodegen)
}

tasks.namedLazily<Task>(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configureEach {
    dependsOn(dependenciesCodegen)
}

tasks.withType<JavaCompile>().configureEach { dependsOn(dependenciesCodegen) }

tasks.withType<KotlinCompile>().configureEach { dependsOn(dependenciesCodegen) }

fun buildConstants() {
    dependencies.forEach { minimalDependency ->
        val fileName = minimalDependency.module.toString().replace(":", "_")
        val dependencyVariableName = fileName.buildDependencyVariableName()
        val dependencyVersion = minimalDependency.versionConstraint.displayName
        buildDir
            .resolve(
                "generated/main/kotlin/" +
                    "com/javiersc/hubdle/extensions/dependencies/_internal/constants/$fileName.kt",
            )
            .apply {
                parentFile.mkdirs()
                createNewFile()
                writeText(
                    """
                        |package com.javiersc.hubdle.extensions.dependencies._internal.constants
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
                    """.trimMargin(),
                )
            }
    }
}

fun buildHubdleDependenciesList() {
    buildDir
        .resolve(
            "generated/main/kotlin/" +
                "com/javiersc/hubdle/extensions/dependencies/_internal/hubdle_dependencies_list.kt",
        )
        .apply {
            parentFile.mkdirs()
            createNewFile()
            val dependenciesAsStringList =
                dependencies
                    .map {
                        val displayName = it.versionConstraint.displayName
                        val version = if (displayName.isNullOrBlank()) "" else ":$displayName"
                        """"${it.module}$version",""".prependIndent("        ")
                    }
                    .sorted()
            writeText(
                """
                    |package com.javiersc.hubdle.extensions.dependencies._internal
                    |
                    |internal val hubdleDependencies: List<String> =
                    |    listOf(
                    ${dependenciesAsStringList.map { "|$it" }.joinToString("\n")}
                    |    )
                    |    
                """.trimMargin(),
            )
        }
}

fun buildHubdleDependencies() {
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
                            val dependencyName = "$groupSanitized$nameSanitized".decapitalize()
                                """
                                    |@HubdleDslMarker
                                    |public fun KotlinDependencyHandler.$dependencyName(): MinimalExternalModuleDependency =
                                    |    catalogDependency(${dependencyVariableName}_MODULE)
                                """
                        }
                        .lines()

                writeText(
                    readText() +
                        """ ${dependencyVariableNames.joinToString("\n")}
                                |
                            """.trimMargin()
                )
            }
        writeText(
            """
                     |import com.javiersc.hubdle.extensions.HubdleDslMarker
                     |import com.javiersc.hubdle.extensions._internal.state.catalogDependency
                     |import com.javiersc.hubdle.extensions.dependencies._internal.constants.*
                     |import org.gradle.api.artifacts.Dependency
                     |import org.gradle.api.artifacts.MinimalExternalModuleDependency
                     |import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
                     |
                     |
                 """.trimMargin() +
                readText().removeDuplicateEmptyLines().endWithNewLine()
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
    replace(".", "_").replace("-", "_").toUpperCase(Locale.getDefault())

val MinimalExternalModuleDependency.hasCommonEndingDomain: Boolean
    get() = module.group.split(".").first().count() <= 3

val MinimalExternalModuleDependency.endAndStartWithSameName: Boolean
    get() = module.name.startsWith(module.group.substringAfterLast("."))

val MinimalExternalModuleDependency.onlyDomain: Boolean
    get() = module.group.split(".").count() == 2
