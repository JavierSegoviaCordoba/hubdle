package com.javiersc.hubdle.project.extensions.shared.features.tasks

import com.javiersc.kotlin.stdlib.TransformString
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import com.javiersc.kotlin.stdlib.remove
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@CacheableTask
public open class GenerateProjectDataTask
@Inject
constructor(
    objects: ObjectFactory,
    packageName: Provider<String>,
    projectGroup: Provider<String>,
    projectName: Provider<String>,
    projectVersion: Provider<String>,
    outputDir: Provider<Directory>,
) : DefaultTask() {

    init {
        group = BasePlugin.BUILD_GROUP
    }

    @Input
    public val packageName: Property<String> = objects.property<String>().convention(packageName)

    @Input
    public val projectGroup: Property<String> = objects.property<String>().convention(projectGroup)

    @Input
    public val projectName: Property<String> = objects.property<String>().convention(projectName)

    @Input
    public val projectVersion: Property<String> =
        objects.property<String>().convention(projectVersion)

    @Internal
    public val objectName: Provider<String> =
        projectName.map { "${it.TransformString()}ProjectData" }

    @OutputFile
    public val outputFile: RegularFileProperty =
        objects.fileProperty().convention {
            val fileName = objectName.map { "$it.kt" }.get()
            outputDir.get().file(fileName).asFile
        }

    @TaskAction
    public fun run() {
        val packageLine =
            if (packageName.get().isNotNullNorBlank()) "package ${packageName.get()}" else null
        val library = "${projectGroup.get()}:${projectName.get()}:${projectVersion.get()}"
        val groupLine = """public const val Group: String = "${projectGroup.get()}""""
        val nameLine = """public const val Name: String = "${projectName.get()}""""
        val versionLine = """public const val Version: String = "${projectVersion.get()}""""
        val libraryLine = """public const val Library: String = "$library""""

        val content = buildString {
            if (packageLine != null) {
                appendLine(packageLine)
                appendLine()
            }
            appendLine("public object ${objectName.get()} {")
            appendLine()
            appendLine(libraryLine.prependIndent())
            appendLine(groupLine.prependIndent())
            appendLine(nameLine.prependIndent())
            appendLine(versionLine.prependIndent())
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

        public const val NAME: String = "generateProjectData"

        internal fun register(project: Project): TaskProvider<GenerateProjectDataTask> {
            val calculatePackageName: Provider<String> = project.calculatePackageName()
            val calculateOutputDir: Provider<Directory> = project.calculatePackageDir()
            return project.tasks.register<GenerateProjectDataTask>(
                NAME,
                calculatePackageName,
                project.provider { "${project.group}" },
                project.provider { project.name },
                project.provider { "${project.version}" },
                calculateOutputDir,
            )
        }

        private fun Project.getMainKotlinSourceSet(): Provider<KotlinSourceSet?> = provider {
            val sourceSets = the<KotlinProjectExtension>().sourceSets
            sourceSets.findByName("main") ?: sourceSets.findByName("commonMain")
        }

        private fun Project.calculatePackageDir(): Provider<Directory> = provider {
            val packageName = calculatePackageName().get()
            val sourceSet = getMainKotlinSourceSet().orNull
            val generatedDir: File =
                sourceSet?.kotlin?.srcDirs?.firstOrNull { it.path.contains("generated") }
                    ?: layout.buildDirectory.file("generated").get().asFile
            val packageDir: Provider<File> = provider {
                if (packageName.isNotBlank()) generatedDir.resolve(packageName.replace(".", "/"))
                else generatedDir
            }

            layout.dir(packageDir).get()
        }

        private fun Project.calculatePackageName(): Provider<String> = provider {
            getMainKotlinSourceSet()
                .orNull
                ?.kotlin
                ?.srcDirs
                ?.flatMap { it.walkTopDown().onLeave { file -> !file.isFile } }
                ?.firstOrNull { it.isFile }
                ?.readLines()
                ?.firstOrNull { line -> line.startsWith("package") }
                ?.substringAfter("package")
                ?.remove(" ")
                ?: ""
        }
    }
}
