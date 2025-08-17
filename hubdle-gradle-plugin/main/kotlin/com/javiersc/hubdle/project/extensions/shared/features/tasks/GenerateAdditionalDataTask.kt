package com.javiersc.hubdle.project.extensions.shared.features.tasks

import com.javiersc.hubdle.project.extensions._internal.isKotlinMultiplatform
import com.javiersc.hubdle.project.extensions._internal.kotlinSourceSetMainOrCommonMainOrNull
import com.javiersc.hubdle.project.extensions._internal.prepareKotlinIdeaImport
import com.javiersc.hubdle.project.tasks.lifecycle.GenerateTask
import com.javiersc.kotlin.stdlib.TransformString
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

@CacheableTask
public open class GenerateAdditionalDataTask @Inject constructor(objects: ObjectFactory) :
    DefaultTask() {

    init {
        group = BasePlugin.BUILD_GROUP
    }

    @Input public val packageName: Property<String> = objects.property<String>()

    @Input public val projectDir: Property<String> = objects.property<String>()

    @Input public val projectName: Property<String> = objects.property<String>()

    @Input public val rootDir: Property<String> = objects.property<String>()

    @Input public val additionalData: Property<String> = objects.property<String>()

    @Internal
    public val objectName: Provider<String> =
        projectName.map { "${it.TransformString()}AdditionalData" }

    @OutputDirectory public val outputDir: DirectoryProperty = objects.directoryProperty()

    @Internal
    public val outputFile: RegularFileProperty =
        objects.fileProperty().convention {
            val fileName = objectName.map { objectName -> "$objectName.kt" }.get()
            outputDir.get().file(fileName).asFile
        }

    @TaskAction
    public fun run() {
        val packageLine =
            if (packageName.get().isNotNullNorBlank()) "package ${packageName.get()}" else null

        val content: String = buildString {
            if (packageLine != null) {
                appendLine(packageLine)
                appendLine()
            }
            appendLine("public object ${objectName.get()} {")
            appendLine()
            for (line in additionalData.orNull.orEmpty().lines()) {
                appendLine(line.prependIndent())
            }
            appendLine("}")
            appendLine()
        }

        outputFile.get().asFile.apply {
            parentFile.mkdirs()
            createNewFile()
            writeText(content)
        }
    }

    public companion object {

        public const val NAME: String = "generateAdditionalData"

        internal fun register(
            project: Project,
            additionalData: Property<String>,
        ): TaskProvider<GenerateAdditionalDataTask> {
            val packageName: Provider<String> =
                project.provider { "${project.group}.${project.name }".replace("-", ".") }
            val projectName: Provider<String> = project.provider { project.name }
            val generateAdditionalDataTask: TaskProvider<GenerateAdditionalDataTask> =
                project.tasks.register<GenerateAdditionalDataTask>(NAME)

            val outputDir: Provider<Directory> =
                project.provider {
                    val setName = if (project.isKotlinMultiplatform) "commonMain" else "main"
                    val kotlinGeneratedDir = "generated/${setName}/kotlin"
                    val packageNameToDir = packageName.get().replace(".", "/")
                    project.layout.buildDirectory.dir("$kotlinGeneratedDir/$packageNameToDir").get()
                }

            generateAdditionalDataTask.configure {
                it.packageName.convention(packageName)
                it.projectName.convention(projectName)
                it.projectDir.convention(project.projectDir.absolutePath)
                it.rootDir.convention(project.rootDir.absolutePath)
                it.outputDir.convention(outputDir)
                it.additionalData.convention(additionalData)
            }
            project.prepareKotlinIdeaImport.configure { task ->
                task.dependsOn(generateAdditionalDataTask)
            }
            project.tasks.named(GenerateTask.NAME).configure { task ->
                task.dependsOn(generateAdditionalDataTask)
            }

            project.kotlinSourceSetMainOrCommonMainOrNull?.configureEach { sourceSet ->
                sourceSet.kotlin.srcDirs(generateAdditionalDataTask)
            }

            return generateAdditionalDataTask
        }
    }
}
