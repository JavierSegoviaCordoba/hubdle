package com.javiersc.hubdle.logic

import java.io.Serializable
import java.util.Locale
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty

open class GenerateHubdleTask
@Inject
constructor(
    private val layout: ProjectLayout,
    objects: ObjectFactory,
) : DefaultTask() {

    @Input val libraries: ListProperty<HubdleDependency> = objects.listProperty()

    @Input val libraryAliases: ListProperty<String> = objects.listProperty()

    @Input val pluginAliases: ListProperty<String> = objects.listProperty()

    @OutputDirectory
    val outputDir: Provider<Directory> = layout.buildDirectory.dir(generatedDependenciesInternalDir)

    @TaskAction
    fun run() {
        buildConstants()
        buildHubdleDependencies()
    }

    private fun buildConstants() {
        libraries.get().forEach { minimalDependency ->
            val fileName = minimalDependency.module.replace(":", "_")
            val dependencyVariableName = fileName.buildDependencyVariableName()
            val dependencyVersion = minimalDependency.version
            outputDir
                .get()
                .asFile
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

    private fun buildHubdleDependencies() {
        layout.buildDirectory
            .get()
            .asFile
            .resolve(generatedDependenciesInternalDir)
            .resolve("constants/HUBDLE_ALIASES.kt")
            .apply {
                parentFile.mkdirs()
                createNewFile()
                val libraryAliases: String =
                    libraryAliases.get().joinToString("\n") { alias ->
                        """|internal const val ${alias.sanitizeAlias()} = "$alias""""
                    }
                val pluginAliases: String =
                    pluginAliases.get().joinToString("\n") { alias ->
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

    private fun String.buildDependencyVariableName(): String =
        replace(".", "_").replace("-", "_").uppercase(Locale.getDefault())

    private fun String.sanitizeAlias() = replace(".", "_")

    data class HubdleDependency(val module: String, val version: String) : Serializable

    companion object {

        private const val generatedDependenciesInternalDir =
            "generated/main/kotlin/com/javiersc/hubdle/project/extensions/dependencies/_internal"
    }
}
