package com.javiersc.hubdle.project.extensions.shared.features.tasks

import com.javiersc.hubdle.project.extensions._internal.allKotlinSrcDirsWithoutBuild
import com.javiersc.hubdle.project.extensions._internal.kotlinGeneratedSrcDirs
import com.javiersc.hubdle.project.extensions._internal.kotlinSrcDirsWithoutBuild
import com.javiersc.kotlin.stdlib.TransformString
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import com.javiersc.kotlin.stdlib.remove
import java.io.File
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.FileTree
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.IgnoreEmptyDirectories
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

@CacheableTask
public open class GenerateProjectDataTask
@Inject
constructor(
    objects: ObjectFactory,
) : SourceTask() {

    init {
        group = BasePlugin.BUILD_GROUP
    }

    @Input public val packageName: Property<String> = objects.property<String>()

    @Input public val projectDir: Property<String> = objects.property<String>()

    @Input public val projectGroup: Property<String> = objects.property<String>()

    @Input public val projectName: Property<String> = objects.property<String>()

    @Input public val projectVersion: Property<String> = objects.property<String>()

    @Input public val rootDir: Property<String> = objects.property<String>()

    @Internal
    public val objectName: Provider<String> =
        projectName.map { "${it.TransformString()}ProjectData" }

    @OutputFile public val outputFile: RegularFileProperty = objects.fileProperty()

    @InputFiles
    @SkipWhenEmpty
    @IgnoreEmptyDirectories
    @PathSensitive(PathSensitivity.RELATIVE)
    override fun getSource(): FileTree {
        return super.getSource()
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

        val projectDirFile = File(projectDir.get())
        val rootDirFile = File(rootDir.get())
        val relativeDirFile = projectDirFile.relativeTo(rootDirFile)

        val rootDirAbsolutePathLine = buildString {
            appendLine("public const val RootDirAbsolutePath: String =")
            appendLine("    \"\"\"${rootDirFile.absolutePath}\"\"\"")
        }
        val rootDirPathLine = buildString {
            appendLine("public const val RootDirPath: String =")
            appendLine("    \"\"\"${rootDirFile.path}\"\"\"")
        }

        val projectDirAbsolutePathLine = buildString {
            appendLine("public const val ProjectDirAbsolutePath: String =")
            appendLine("    \"\"\"${projectDirFile.absolutePath}\"\"\"")
        }
        val projectDirPathLine = buildString {
            appendLine("public const val ProjectDirPath: String =")
            appendLine("    \"\"\"${projectDirFile.path}\"\"\"")
        }

        val relativeDirPathLine = buildString {
            appendLine("public const val RelativeDirPath: String =")
            appendLine("    \"\"\"${relativeDirFile.path}\"\"\"")
        }

        val content: String = buildString {
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
            appendLine(rootDirAbsolutePathLine.prependIndent())
            appendLine(rootDirPathLine.prependIndent())
            appendLine(projectDirAbsolutePathLine.prependIndent())
            appendLine(projectDirPathLine.prependIndent())
            appendLine(relativeDirPathLine.prependIndent())
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
            val packageName: Provider<String> = project.calculatePackageName()
            val outputDir: Provider<Directory> = project.calculatePackageDir()
            val projectGroup = project.provider { "${project.group}" }
            val projectName = project.provider { project.name }
            val projectVersion = project.provider { "${project.version}" }
            val generateProjectDataTask = project.tasks.register<GenerateProjectDataTask>(NAME)
            generateProjectDataTask.configure {
                it.source(project.allKotlinSrcDirsWithoutBuild)
                it.packageName.set(packageName)
                it.projectDir.set(project.projectDir.absolutePath)
                it.projectGroup.set(projectGroup)
                it.projectName.set(projectName)
                it.projectVersion.set(projectVersion)
                it.rootDir.set(project.rootDir.absolutePath)
                it.outputFile.convention {
                    val fileName = it.objectName.map { objectName -> "$objectName.kt" }.get()
                    outputDir.get().file(fileName).asFile
                }
            }
            return generateProjectDataTask
        }

        private fun Project.calculatePackageDir(): Provider<Directory> = provider {
            val packageName = calculatePackageName().get()
            val generatedDir: File = file(kotlinGeneratedSrcDirs.get().first())
            val packageDir: Provider<File> = provider {
                if (packageName.isNotBlank()) generatedDir.resolve(packageName.replace(".", "/"))
                else generatedDir
            }
            layout.dir(packageDir).get()
        }

        private fun Project.calculatePackageName(): Provider<String> = provider {
            kotlinSrcDirsWithoutBuild
                .get()
                .flatMap { it.walkTopDown() }
                .firstOrNull { file -> file.walkTopDown().maxDepth(1).any { it.isFile } }
                ?.walkTopDown()
                ?.maxDepth(1)
                ?.firstOrNull { it.isFile }
                ?.readLines()
                ?.firstOrNull { line -> line.startsWith("package") }
                ?.substringAfter("package")
                ?.remove(" ")
                ?: ""
        }
    }
}
