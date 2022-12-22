package com.javiersc.hubdle.extensions.kotlin.gradle.plugin

import com.gradle.publish.PluginBundleExtension
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions._internal.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions._internal.configureDependencies
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.publishing._internal.configurableMavenPublishing
import com.javiersc.hubdle.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.features.HubdleKotlinGradlePluginFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlin
import com.javiersc.hubdle.extensions.shared.HubdleGradleDependencies
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugin.devel.tasks.PluginUnderTestMetadata
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

// TODO: Move to Kotlin/Jvm/Features
@HubdleDslMarker
public open class HubdleKotlinGradlePluginExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project), HubdleGradleDependencies {

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    public val features: HubdleKotlinGradlePluginFeaturesExtension
        get() = getHubdleExtension()

    public val tags: SetProperty<String> = setProperty { emptySet() }

    @HubdleDslMarker
    public fun tags(vararg tags: String) {
        this.tags.addAll(tags.toList())
    }

    @HubdleDslMarker
    public fun kotlin(action: Action<KotlinJvmProjectExtension>) {
        userConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun gradlePlugin(action: Action<GradlePluginDevelopmentExtension>) {
        userConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun main(action: Action<KotlinSourceSet> = Action {}) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named("main", action::execute) }
        }
    }

    @HubdleDslMarker
    public fun functionalTest(action: Action<KotlinSourceSet> = Action {}) {
        userConfigurable {
            configure<KotlinProjectExtension> {
                sourceSets.named("functionalTest", action::execute)
            }
        }
    }

    @HubdleDslMarker
    public fun integrationTest(action: Action<KotlinSourceSet> = Action {}) {
        userConfigurable {
            configure<KotlinProjectExtension> {
                sourceSets.named("integrationTest", action::execute)
            }
        }
    }

    @HubdleDslMarker
    public fun test(action: Action<KotlinSourceSet> = Action {}) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named("test", action::execute) }
        }
    }

    @HubdleDslMarker
    public fun testFixtures(action: Action<KotlinSourceSet> = Action {}) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named("testFixtures", action::execute) }
        }
    }

    public val pluginUnderTestDependencies: ListProperty<MinimalExternalModuleDependency> =
        listProperty {
            emptyList()
        }

    @HubdleDslMarker
    public fun pluginUnderTestDependencies(
        vararg pluginUnderTestDependencies: MinimalExternalModuleDependency
    ) {
        this.pluginUnderTestDependencies.addAll(pluginUnderTestDependencies.toList())
    }

    override fun Project.defaultConfiguration() {
        applicablePlugins()

        configurable {
            configureDefaultJavaSourceSets()
            configureDefaultKotlinSourceSets()
            configureDependencies()
            configureGradleDependencies()
            configureGradlePluginTestSourceSets()
            configurePluginUnderTestDependencies()
            configureGradlePluginTestTasks()
        }

        configureGradlePluginPublishing()
    }

    private fun applicablePlugins() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinJvm,
        )
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JavaGradlePlugin,
        )
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JavaTestFixtures,
        )
    }

    private fun configureGradlePluginTestSourceSets() =
        with(project) {
            configure<SourceSetContainer> {
                maybeCreate("functionalTest")
                maybeCreate("integrationTest")
            }
            configure<KotlinProjectExtension> {
                sourceSets.maybeCreate("functionalTest")
                sourceSets.maybeCreate("integrationTest")
            }
        }

    private fun configureGradlePluginTestTasks() =
        with(project) {
            val functionalTest = the<SourceSetContainer>().named("functionalTest")
            val integrationTest = the<SourceSetContainer>().named("integrationTest")

            val integrationTestTask =
                tasks.maybeRegisterLazily<Test>("integrationTest") { task ->
                    task.description = "Runs the integration tests."
                    task.group = "verification"

                    task.testClassesDirs = functionalTest.get().output.classesDirs
                    task.classpath = functionalTest.get().runtimeClasspath
                    task.mustRunAfter(tasks.namedLazily<Task>("test"))
                }

            val functionalTestTask =
                tasks.maybeRegisterLazily<Test>("functionalTest") { task ->
                    task.description = "Runs the functional tests."
                    task.group = "verification"

                    task.testClassesDirs = integrationTest.get().output.classesDirs
                    task.classpath = integrationTest.get().runtimeClasspath
                    task.mustRunAfter(tasks.namedLazily<Task>("test"))
                }

            tasks.namedLazily<Task>("allTests") { task ->
                task.dependsOn(functionalTestTask)
                task.dependsOn(integrationTestTask)
            }

            tasks.namedLazily<Task>("check") { task ->
                task.dependsOn(functionalTestTask)
                task.dependsOn(integrationTestTask)
            }
        }

    private fun configurePluginUnderTestDependencies() =
        with(project) {
            val pluginUnderTestDependencies = pluginUnderTestDependencies.get()
            if (pluginUnderTestDependencies.isNotEmpty()) {
                val testPluginClasspath: Configuration =
                    configurations.create("testPluginClasspath")

                dependencies {
                    for (dependency in pluginUnderTestDependencies) {
                        testPluginClasspath(dependency)
                    }
                }

                tasks.withType<PluginUnderTestMetadata>().configureEach { metadata ->
                    metadata.pluginClasspath.from(testPluginClasspath)
                }
            }
        }

    private fun configureGradleDependencies() {
        configure<KotlinProjectExtension> {
            sourceSets.named("functionalTest") { it.configureTestDependencies() }
            sourceSets.named("integrationTest") { it.configureTestDependencies() }
            sourceSets.named("test") { it.configureTestDependencies() }
            sourceSets.named("testFixtures") { it.configureTestDependencies() }
        }
    }

    private fun KotlinSourceSet.configureTestDependencies() {
        dependencies {
            implementation(project)
            implementation(project.dependencies.gradleTestKit())
            if (name == "testFixtures") implementation(project.dependencies.testFixtures(project))
        }
    }

    private fun configureGradlePluginPublishing() =
        with(project) {
            applicablePlugin(
                isEnabled = property { isFullEnabled.get() && hubdlePublishing.isEnabled.get() },
                priority = Priority.P3,
                scope = Scope.CurrentProject,
                pluginId = PluginId.GradlePluginPublish,
            )
            configurableMavenPublishing(mavenPublicationName = "java") {
                configure<PluginBundleExtension> {
                    tags = hubdleGradlePlugin.tags.get()
                    website = getProperty(HubdleProperty.POM.url)
                    vcsUrl = getProperty(HubdleProperty.POM.scmUrl)
                }
            }
        }
}

internal val HubdleEnableableExtension.hubdleGradlePlugin: HubdleKotlinGradlePluginExtension
    get() = getHubdleExtension()

internal val Project.hubdleGradlePlugin: HubdleKotlinGradlePluginExtension
    get() = getHubdleExtension()
