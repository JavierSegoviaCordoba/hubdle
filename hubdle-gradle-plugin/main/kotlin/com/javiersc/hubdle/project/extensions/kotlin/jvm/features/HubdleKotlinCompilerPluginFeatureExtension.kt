package com.javiersc.hubdle.project.extensions.kotlin.jvm.features

import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions._internal.libraryModule
import com.javiersc.hubdle.project.extensions._internal.libraryPlatform
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_kotlin_kotlinCompilerExtensions
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_kotlin_kotlinCompilerTestExtensions
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinAnnotationsJvm
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinCompiler
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinCompilerInternalTestFramework
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinReflect
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinScriptRuntime
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinTest
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinTestAnnotationsCommon
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_bom
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_jupiter_junitJupiter
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_platform_junitCommons
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_platform_junitLauncher
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_platform_junitRunner
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_platform_junitSuiteApi
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.KotlinCompilerTestType.Box
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.KotlinCompilerTestType.Diagnostics
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.compiler.GenerateMetaRuntimeClasspathProviderTask
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.shared.features.tasks.GenerateProjectDataTask
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public open class HubdleKotlinCompilerPluginFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinJvm)

    override val priority: Priority = Priority.P4

    public val generateTestOnSync: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun generateTestOnSync(value: Boolean) {
        generateTestOnSync.set(value)
    }

    public val mainClass: Property<String> = property { "" }

    @HubdleDslMarker
    public fun mainClass(value: String) {
        mainClass.set(value)
    }

    public val testDataDir: Property<String> = property { "test-data" }

    @HubdleDslMarker
    public fun testDataDir(value: String) {
        testDataDir.set(value)
    }

    public val testDependencies: SetProperty<MinimalExternalModuleDependency> = setProperty {
        emptySet()
    }

    @HubdleDslMarker
    public fun testDependencies(vararg dependencies: Provider<MinimalExternalModuleDependency>) {
        testDependencies.set(setProperty { dependencies.map { it.get() }.toSet() })
    }

    public val testGenDir: Property<String> = property { "test-gen/java" }

    @HubdleDslMarker
    public fun testGenDir(value: String) {
        testGenDir.set(value)
    }

    public val testProjects: SetProperty<ProjectDependency> = setProperty { emptySet() }

    @HubdleDslMarker
    public fun testProjects(vararg project: ProjectDependency) {
        testProjects.set(project.toSet())
    }

    public val testTypes: SetProperty<KotlinCompilerTestType> = setProperty {
        setOf(Box, Diagnostics)
    }

    @HubdleDslMarker
    public fun testTypes(vararg values: KotlinCompilerTestType) {
        testTypes.set(values.toSet())
    }

    override fun Project.defaultConfiguration() {
        configurable {
            val testSourceSet = the<SourceSetContainer>().named("test")
            testSourceSet.configure { sourceSet -> sourceSet.java.srcDirs(testGenDir.get()) }
            configure<KotlinProjectExtension> {
                sourceSets.configureEach { kotlinSourceSet ->
                    kotlinSourceSet.languageSettings { optInExperimentalAPIs() }
                }

                sourceSets.named("main").configure { kotlinSourceSet ->
                    kotlinSourceSet.dependencies {
                        compileOnly(library(jetbrains_kotlin_kotlinCompiler))
                        implementation(library(javiersc_kotlin_kotlinCompilerExtensions))
                    }
                }

                sourceSets.named("test").configure { kotlinSourceSet ->
                    kotlinSourceSet.dependencies {
                        implementation(library(javiersc_kotlin_kotlinCompilerTestExtensions))
                        implementation(library(jetbrains_kotlin_kotlinCompiler))
                        implementation(
                            library(jetbrains_kotlin_kotlinCompilerInternalTestFramework)
                        )
                        implementation(library(jetbrains_kotlin_kotlinReflect))
                        implementation(library(jetbrains_kotlin_kotlinTestAnnotationsCommon))
                        implementation(libraryModule(junit_jupiter_junitJupiter))
                        implementation(libraryModule(junit_platform_junitCommons))
                        implementation(libraryModule(junit_platform_junitLauncher))
                        implementation(libraryModule(junit_platform_junitRunner))
                        implementation(libraryModule(junit_platform_junitSuiteApi))
                        implementation(platform(libraryPlatform(junit_bom)))
                        runtimeOnly(library(jetbrains_kotlin_kotlinAnnotationsJvm))
                        runtimeOnly(library(jetbrains_kotlin_kotlinScriptRuntime))
                        runtimeOnly(library(jetbrains_kotlin_kotlinTest))
                    }
                }
            }

            tasks.apply {
                val generateMetaRuntimeClasspathProvider =
                    GenerateMetaRuntimeClasspathProviderTask.register(
                        project,
                        mainClass,
                        testDependencies,
                        testProjects,
                    )

                val generateProjectData =
                    namedLazily<GenerateProjectDataTask>(GenerateProjectDataTask.NAME)

                named(BasePlugin.ASSEMBLE_TASK_NAME).configure { task ->
                    task.dependsOn(generateMetaRuntimeClasspathProvider)
                    task.dependsOn(generateProjectData)
                }

                withType<KotlinCompile>().configureEach { kotlinCompile ->
                    kotlinCompile.dependsOn(generateMetaRuntimeClasspathProvider)
                    kotlinCompile.dependsOn(generateProjectData)
                }

                val generateKotlinCompilerTests: TaskProvider<JavaExec> =
                    register<JavaExec>("generateKotlinCompilerTests") { group = "build" }
                generateKotlinCompilerTests.configure { task ->
                    task.doFirst {
                        for (testType: KotlinCompilerTestType in testTypes.get()) {
                            projectDir.resolve("${testDataDir.get()}/${testType.dir}").mkdirs()
                        }
                        projectDir.resolve(testGenDir.get()).mkdirs()
                    }
                    task.isEnabled = mainClass.orNull.isNotNullNorBlank()
                    task.classpath = testSourceSet.get().runtimeClasspath
                    task.mainClass.set(mainClass)
                    task.dependsOn(generateMetaRuntimeClasspathProvider)
                    task.dependsOnTestProjects()
                }

                named("prepareKotlinIdeaImport").configure { task ->
                    if (mainClass.orNull.isNotNullNorBlank() && generateTestOnSync.orNull == true) {
                        task.dependsOn(generateKotlinCompilerTests)
                        task.dependsOn(generateMetaRuntimeClasspathProvider)
                        task.dependsOn(generateProjectData)
                    }
                }

                tasks.withType<Jar>().configureEach { jar -> jar.dependsOn(generateProjectData) }

                named<Test>("test") {
                    testLogging { it.showStandardStreams = true }

                    useJUnitPlatform()

                    doFirst {
                        for ((group: String, name: String) in libraryProperties) {
                            setLibraryProperty(propName = group, jarName = name)
                        }
                    }

                    dependsOn(generateKotlinCompilerTests)
                    dependsOnTestProjects()
                }
            }
        }
    }

    private fun Task.dependsOnTestProjects() {
        for (projectDependency: ProjectDependency in testProjects.get()) {
            val project: Project = projectDependency.dependencyProject
            val jarTaskName: String =
                when {
                    project.pluginManager.hasPlugin(PluginId.JetbrainsKotlinMultiplatform.id) ->
                        "jvmJar"
                    project.pluginManager.hasPlugin(PluginId.JetbrainsKotlinJvm.id) -> "jar"
                    else -> "jar"
                }
            dependsOn("${project.path}:$jarTaskName")
        }
    }

    private fun LanguageSettingsBuilder.optInExperimentalAPIs() {
        optIn("kotlin.RequiresOptIn")
        optIn("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
        optIn("org.jetbrains.kotlin.diagnostics.InternalDiagnosticFactoryMethod")
        optIn("org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI")
        optIn("org.jetbrains.kotlin.fir.PrivateForInline")
        optIn("org.jetbrains.kotlin.fir.resolve.dfa.DfaInternals")
        optIn("org.jetbrains.kotlin.fir.resolve.transformers.AdapterForResolveProcessor")
        optIn("org.jetbrains.kotlin.fir.symbols.SymbolInternals")
    }

    private val libraryProperties: Map<String, String> =
        mapOf(
            "org.jetbrains.kotlin.test.kotlin-stdlib" to "kotlin-stdlib",
            "org.jetbrains.kotlin.test.kotlin-stdlib-jdk8" to "kotlin-stdlib-jdk8",
            "org.jetbrains.kotlin.test.kotlin-test" to "kotlin-test",
            "org.jetbrains.kotlin.test.kotlin-script-runtime" to "kotlin-script-runtime",
            "org.jetbrains.kotlin.test.kotlin-annotations-jvm" to "kotlin-annotations-jvm",
        )

    private fun Test.setLibraryProperty(propName: String, jarName: String) {
        val path =
            project.configurations["testRuntimeClasspath"]
                .files
                .find { """$jarName-\d.*jar""".toRegex().matches(it.name) }
                ?.absolutePath
                ?: return
        systemProperty(propName, path)
    }
}

public enum class KotlinCompilerTestType(internal val dir: String) {
    Box("box"),
    Diagnostics("diagnostics"),
}

public interface HubdleKotlinCompilerPluginDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val compiler: HubdleKotlinCompilerPluginFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun compiler(action: Action<HubdleKotlinCompilerPluginFeatureExtension> = Action {}) {
        compiler.enableAndExecute(action)
    }
}
