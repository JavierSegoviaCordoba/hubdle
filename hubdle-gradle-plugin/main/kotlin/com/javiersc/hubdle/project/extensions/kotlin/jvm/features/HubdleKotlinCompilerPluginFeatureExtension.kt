package com.javiersc.hubdle.project.extensions.kotlin.jvm.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.kotlinSourceSetMainOrCommonMain
import com.javiersc.hubdle.project.extensions._internal.kotlinSourceSetTestOrCommonTest
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions._internal.libraryModule
import com.javiersc.hubdle.project.extensions._internal.libraryPlatform
import com.javiersc.hubdle.project.extensions._internal.prepareKotlinIdeaImport
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_kotlin_compiler_extensions
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_kotlin_compiler_test_extensions
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_annotations_jvm
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_compiler
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_compiler_internal_test_framework
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_reflect
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_script_runtime
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_test
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_test_annotations_common
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_bom
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_jupiter_junit_jupiter
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_jupiter_junit_jupiter_api
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_platform_junit_platform_commons
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_platform_junit_platform_launcher
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_platform_junit_platform_runner
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.junit_platform_junit_platform_suite_api
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.KotlinCompilerTestType.Box
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.KotlinCompilerTestType.Diagnostics
import com.javiersc.hubdle.project.extensions.kotlin.jvm.features.compiler.GenerateMetaRuntimeClasspathProviderTask
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.tasks.lifecycle.GenerateTask
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.ProjectDependency
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

public open class HubdleKotlinCompilerPluginFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinJvm)

    public val addExtensionDependencies: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun addExtensionDependencies(value: Boolean) {
        addExtensionDependencies.set(value)
    }

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

    override fun Project.defaultConfiguration() = lazyConfigurable {
        configure<SourceSetContainer> {
            register("test-data").configure {
                val testData = objects.sourceDirectorySet("test-data", "test-data")
                testData.filter.include("**/*.fir.ir.txt", "**/*.fir.txt", "**/*.kt")
            }
        }

        the<KotlinProjectExtension>().sourceSets.configureEach { kotlinSourceSet ->
            kotlinSourceSet.languageSettings { optInExperimentalAPIs() }
        }

        kotlinSourceSetMainOrCommonMain.configureEach { kotlinSourceSet ->
            kotlinSourceSet.dependencies {
                compileOnly(library(jetbrains_kotlin_compiler))
                if (addExtensionDependencies.get()) {
                    implementation(library(javiersc_kotlin_compiler_extensions))
                }
            }
        }

        kotlinSourceSetTestOrCommonTest.configureEach { kotlinSourceSet ->
            kotlinSourceSet.dependencies {
                if (addExtensionDependencies.get()) {
                    implementation(library(javiersc_kotlin_compiler_test_extensions))
                }
                implementation(library(jetbrains_kotlin_compiler))
                implementation(library(jetbrains_kotlin_compiler_internal_test_framework))
                implementation(library(jetbrains_kotlin_reflect))
                implementation(library(jetbrains_kotlin_test_annotations_common))
                implementation(libraryModule(junit_jupiter_junit_jupiter))
                implementation(libraryModule(junit_jupiter_junit_jupiter_api))
                implementation(libraryModule(junit_platform_junit_platform_commons))
                implementation(libraryModule(junit_platform_junit_platform_launcher))
                implementation(libraryModule(junit_platform_junit_platform_runner))
                implementation(libraryModule(junit_platform_junit_platform_suite_api))
                implementation(dependencies.platform(libraryPlatform(junit_bom)))
                runtimeOnly(library(jetbrains_kotlin_annotations_jvm))
                runtimeOnly(library(jetbrains_kotlin_script_runtime))
                runtimeOnly(library(jetbrains_kotlin_test))
            }
        }

        val generateMetaRuntimeClasspathProvider:
            TaskProvider<GenerateMetaRuntimeClasspathProviderTask> =
            GenerateMetaRuntimeClasspathProviderTask.register(
                project,
                mainClass,
                testDependencies,
                testProjects,
            )

        kotlinSourceSetTestOrCommonTest.configureEach {
            it.kotlin.srcDirs(generateMetaRuntimeClasspathProvider)
        }

        val generateKotlinCompilerTests: TaskProvider<JavaExec> =
            tasks.register<JavaExec>("generateKotlinCompilerTests") { group = "build" }

        val testSourceSet = the<SourceSetContainer>().named("test")
        testSourceSet.configure { sourceSet -> sourceSet.java.srcDirs(testGenDir.get()) }

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

        tasks.named(GenerateTask.NAME).configure { task ->
            task.dependsOn(generateKotlinCompilerTests)
        }

        prepareKotlinIdeaImport.configure { task ->
            if (mainClass.orNull.isNotNullNorBlank() && generateTestOnSync.orNull == true) {
                task.dependsOn(generateKotlinCompilerTests)
                task.dependsOn(generateMetaRuntimeClasspathProvider)
            }
        }

        tasks.named<Test>("test") {
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

    private fun Task.dependsOnTestProjects() {
        dependsOn(
            testProjects.map { projectDependencies ->
                projectDependencies.map { projectDependency: ProjectDependency ->
                    projectDependency.dependencyProject.tasks.withType<Jar>()
                }
            }
        )
    }

    private fun LanguageSettingsBuilder.optInExperimentalAPIs() {
        optIn("kotlin.RequiresOptIn")
        optIn("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
        optIn("org.jetbrains.kotlin.diagnostics.InternalDiagnosticFactoryMethod")
        optIn("org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI")
        optIn("org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI")
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
                ?.absolutePath ?: return
        systemProperty(propName, path)
    }
}

public enum class KotlinCompilerTestType(internal val dir: String) {
    Box("box"),
    Diagnostics("diagnostics"),
}

public interface HubdleKotlinCompilerPluginDelegateFeatureExtension : BaseHubdleExtension {

    public val compiler: HubdleKotlinCompilerPluginFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun compiler(action: Action<HubdleKotlinCompilerPluginFeatureExtension> = Action {}) {
        compiler.enableAndExecute(action)
    }
}
