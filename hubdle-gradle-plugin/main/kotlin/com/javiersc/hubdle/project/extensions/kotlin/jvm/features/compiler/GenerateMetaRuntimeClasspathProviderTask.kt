package com.javiersc.hubdle.project.extensions.kotlin.jvm.features.compiler

import com.javiersc.gradle.project.extensions.module
import com.javiersc.hubdle.project.extensions._internal.allKotlinSrcDirsWithoutBuild
import com.javiersc.hubdle.project.extensions.kotlin.shared.moduleAsString
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class GenerateMetaRuntimeClasspathProviderTask
@Inject
constructor(
    objects: ObjectFactory,
    layout: ProjectLayout,
) : SourceTask() {

    @Input public val mainClass: Property<String> = objects.property<String>()

    @Input
    public val testDependenciesJarPaths: ListProperty<String> = objects.listProperty<String>()

    @Input public val testProjectsJarPaths: ListProperty<String> = objects.listProperty<String>()

    @OutputDirectory
    public val generatedTestDir: DirectoryProperty =
        objects
            .directoryProperty()
            .convention(
                layout.buildDirectory.dir("generated/test/kotlin/").map { kotlinDir ->
                    val generatedPath =
                        project.module
                            .replace("-", "/")
                            .replace(".", "/")
                            .replace(":", "/")
                            .replace("_", "/")
                    val mainClassString: String = mainClass.get()
                    val mainClassPackage = mainClassString.substringBeforeLast('.')
                    val mainClassParentDir = mainClassPackage.replace('.', '/')
                    kotlinDir.dir(generatedPath).dir(mainClassParentDir)
                }
            )

    @Internal
    public val outputFile: RegularFileProperty =
        objects
            .fileProperty()
            .convention(generatedTestDir.file("GeneratedMetaRuntimeClasspathProvider.kt"))

    init {
        group = "build"
        testProjectsJarPaths.orNull?.isNotEmpty() == true && mainClass.orNull.isNotNullNorBlank()
    }

    @TaskAction
    public fun run() {
        val mainClassPackage = mainClass.get().substringBeforeLast('.')

        val content: String = buildString {
            appendLine("package $mainClassPackage")
            appendLine()
            appendLine(
                "import com.javiersc.kotlin.compiler.test.services.MetaRuntimeClasspathProvider"
            )
            appendLine("import org.jetbrains.kotlin.test.services.TestServices")
            appendLine()
            appendLine("open class GeneratedMetaRuntimeClasspathProvider(")
            appendLine("    testServices: TestServices,")
            appendLine(") : MetaRuntimeClasspathProvider(testServices) {")
            appendLine("    override val jarPaths: List<String> =")
            appendLine("        listOf(")
            val jarPaths: List<String> = testDependenciesJarPaths.get() + testProjectsJarPaths.get()
            for (jarPath: String in jarPaths) {
                val sanitizedJarPath = "\"\"\"$jarPath\"\"\""
                appendLine("""           $sanitizedJarPath,""")
            }
            appendLine("        )")
            appendLine("}")
            appendLine()
        }

        outputFile.orNull?.asFile?.apply {
            parentFile.mkdirs()
            createNewFile()
            writeText(content)
        }
    }

    public companion object {
        public const val NAME: String = "generateMetaRuntimeClasspathProvider"

        internal fun register(
            project: Project,
            mainClass: Property<String>,
            testDependencies: SetProperty<MinimalExternalModuleDependency>,
            testProjects: SetProperty<ProjectDependency>,
        ): TaskProvider<GenerateMetaRuntimeClasspathProviderTask> {
            val testDependenciesJarPaths: Provider<List<String>> =
                project.provider {
                    val artifacts =
                        project.configurations["runtimeClasspath"]
                            .resolvedConfiguration
                            .resolvedArtifacts
                    val modules = testDependencies.get().map { it.moduleAsString() }
                    // find Kotlin Multiplatform modules
                    val modulesJvmSuffix = modules.map { "$it-jvm" }
                    artifacts
                        .filter { artifact ->
                            val module: String =
                                "${artifact.moduleVersion}"
                                    .dropLastWhile { char -> char != ':' }
                                    .dropLast(1)
                            (module in modules || module in modulesJvmSuffix)
                        }
                        .map { it.file.path }
                }

            val testProjectsJarPaths: Provider<List<String>> =
                project.provider {
                    testProjects
                        .get()
                        .flatMap { projectDependency ->
                            val projectName = projectDependency.dependencyProject.name
                            val projectVersion = "${projectDependency.dependencyProject.version}"
                            val jars =
                                projectDependency.dependencyProject.buildDir
                                    .resolve("libs")
                                    .walkTopDown()
                                    .maxDepth(1)
                            jars.filter { jar ->
                                val hasVersion = jar.name.contains(projectVersion)
                                val isNotMetadata = !jar.name.startsWith("$projectName-metadata")
                                hasVersion && isNotMetadata
                            }
                        }
                        .map { it.path }
                }

            val generateMetaRuntimeClasspathProviderTask =
                project.tasks.register<GenerateMetaRuntimeClasspathProviderTask>(NAME)
            generateMetaRuntimeClasspathProviderTask.configure {
                it.mainClass.convention(mainClass)
                it.testDependenciesJarPaths.convention(testDependenciesJarPaths)
                it.testProjectsJarPaths.convention(testProjectsJarPaths)
                it.source(project.allKotlinSrcDirsWithoutBuild)
            }
            return generateMetaRuntimeClasspathProviderTask
        }
    }
}
