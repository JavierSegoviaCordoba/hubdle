package com.javiersc.hubdle.project.extensions.kotlin.jvm.features.compiler

import com.javiersc.gradle.properties.extensions.listProperty
import com.javiersc.gradle.properties.extensions.property
import com.javiersc.hubdle.project.extensions.kotlin.shared.moduleAsString
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import io.gitlab.arturbosch.detekt.Detekt
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@CacheableTask
public abstract class GenerateMetaRuntimeClasspathProviderTask
@Inject
constructor(
    generatedTestSourceSetDir: Property<String>,
    mainClass: Property<String>,
    testDependenciesJarPaths: ListProperty<String>,
    testProjectsJarPaths: ListProperty<String>,
    objects: ObjectFactory,
) : DefaultTask() {

    @Input
    public val generatedTestSourceSetDir: Property<String> =
        objects.property<String>().convention(generatedTestSourceSetDir)

    @Input public val mainClass: Property<String> = objects.property<String>().convention(mainClass)

    @Input
    public val testDependenciesJarPaths: ListProperty<String> =
        objects.listProperty<String>().convention(testDependenciesJarPaths)

    @Input
    public val testProjectsJarPaths: ListProperty<String> =
        objects.listProperty<String>().convention(testProjectsJarPaths)

    @OutputFile
    public val generatedContentFile: RegularFileProperty =
        objects.fileProperty().convention {
            val mainClassString: String = mainClass.get()
            val mainClassPackage = mainClassString.substringBeforeLast('.')
            val mainClassParentDir = mainClassPackage.replace('.', '/')
            File(generatedTestSourceSetDir.get())
                .resolve(mainClassParentDir)
                .resolve("GeneratedMetaRuntimeClasspathProvider.kt")
        }

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

        generatedContentFile.orNull?.asFile?.apply {
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
            val generatedTestSourceSetDir: Property<String> =
                project.property {
                    val testSet: KotlinSourceSet =
                        project
                            .the<KotlinProjectExtension>()
                            .sourceSets
                            .named<KotlinSourceSet>("test")
                            .get()
                    testSet.kotlin.srcDirs.firstOrNull { it.path.contains("generated") }?.path
                        ?: project.buildDir.resolve("generated/test/kotlin").path
                }

            val testDependenciesJarPaths: ListProperty<String> =
                project.listProperty {
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

            val testProjectsJarPaths: ListProperty<String> =
                project.listProperty {
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

            return project.tasks
                .register<GenerateMetaRuntimeClasspathProviderTask>(
                    NAME,
                    generatedTestSourceSetDir,
                    mainClass,
                    testDependenciesJarPaths,
                    testProjectsJarPaths,
                )
                .apply {
                    project.tasks.withType<Detekt>().configureEach { detekt ->
                        detekt.mustRunAfter(this)
                    }
                }
        }
    }
}
