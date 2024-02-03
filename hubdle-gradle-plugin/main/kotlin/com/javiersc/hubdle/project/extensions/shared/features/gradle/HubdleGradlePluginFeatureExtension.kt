package com.javiersc.hubdle.project.extensions.shared.features.gradle

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.TEST
import com.javiersc.hubdle.project.extensions._internal.TEST_FIXTURES
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.project.extensions._internal.TEST_INTEGRATION
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.publishing.maven.hubdlePublishingMavenPom
import com.javiersc.hubdle.project.extensions.config.publishing.tasks.CheckIsSemverTask
import com.javiersc.hubdle.project.extensions.config.versioning.semver._internal.isTagPrefixProject
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_gradle_extensions
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_gradle_test_extensions
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.shared.HubdleGradleDependencies
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.internal.catalog.DelegatingProjectDependency
import org.gradle.api.internal.catalog.ExternalModuleDependencyFactory.DependencyNotationSupplier
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderConvertible
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugin.devel.tasks.PluginUnderTestMetadata

public open class HubdleGradlePluginFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project), HubdleGradleDependencies {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJava, hubdleKotlinJvm)

    override val priority: Priority = Priority.P4

    public val extendedGradle: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun extendedGradle(extendedGradle: Boolean) {
        this.extendedGradle.set(extendedGradle)
    }

    @HubdleDslMarker
    public fun gradlePlugin(action: Action<GradlePluginDevelopmentExtension>) {
        userConfigurable { action.execute(the()) }
    }

    public val pluginUnderTestDependencies: ListProperty<String> = listProperty { emptyList() }

    public val pluginUnderTestExternalDependencies: ListProperty<MinimalExternalModuleDependency> =
        listProperty {
            emptyList()
        }

    public val pluginUnderTestProjects: ListProperty<DelegatingProjectDependency> = listProperty {
        emptyList()
    }

    @HubdleDslMarker
    public fun pluginUnderTestDependencies(vararg dependencies: Any) {
        for (dependency in dependencies) {
            when (dependency) {
                is String -> {
                    pluginUnderTestDependencies.add(dependency)
                }
                is MinimalExternalModuleDependency -> {
                    pluginUnderTestExternalDependencies(dependency)
                }
                is DelegatingProjectDependency -> {
                    pluginUnderTestProjects(dependency)
                }
                is DependencyNotationSupplier -> {
                    pluginUnderTestExternalDependencies(dependency.asProvider())
                }
                is Provider<*> -> {
                    val string: String? = dependency.orNull as? String
                    string?.let { pluginUnderTestDependencies.add(it) }

                    val minimalExternalModuleDependency: MinimalExternalModuleDependency? =
                        dependency.orNull as? MinimalExternalModuleDependency
                    minimalExternalModuleDependency?.let { pluginUnderTestExternalDependencies(it) }

                    val projectDependency: DelegatingProjectDependency? =
                        dependency.orNull as? DelegatingProjectDependency
                    projectDependency?.let { pluginUnderTestProjects(it) }
                }
                else -> {
                    throw IllegalArgumentException("Unknown type: ${dependency::class.simpleName}")
                }
            }
        }
    }

    @HubdleDslMarker
    public fun pluginUnderTestExternalDependencies(
        vararg dependencies: ProviderConvertible<MinimalExternalModuleDependency>
    ) {
        this.pluginUnderTestExternalDependencies.addAll(
            provider { dependencies.map { it.asProvider().get() } }
        )
    }

    @HubdleDslMarker
    public fun pluginUnderTestExternalDependencies(
        vararg dependencies: MinimalExternalModuleDependency
    ) {
        this.pluginUnderTestExternalDependencies.addAll(dependencies.toList())
    }

    @HubdleDslMarker
    public fun pluginUnderTestExternalDependencies(
        vararg dependencies: Provider<MinimalExternalModuleDependency>
    ) {
        this.pluginUnderTestExternalDependencies.addAll(provider { dependencies.map { it.get() } })
    }

    @HubdleDslMarker
    public fun pluginUnderTestProjects(vararg projects: DelegatingProjectDependency) {
        this.pluginUnderTestProjects.addAll(projects.toList())
    }

    @HubdleDslMarker
    public fun pluginUnderTestProjects(vararg projects: Provider<DelegatingProjectDependency>) {
        this.pluginUnderTestProjects.addAll(provider { projects.map { it.get() } })
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JavaGradlePlugin,
        )

        applicablePlugin(
            isEnabled = property { isFullEnabled.get() && hubdlePublishing.isFullEnabled.get() },
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.GradlePluginPublish,
        )

        configurable {
            tasks.named("publishPlugins").configure { task ->
                task.enabled = isTagPrefixProject
                task.dependsOn(CheckIsSemverTask.NAME)
            }

            val dependencies = pluginUnderTestDependencies.get()
            val externalDependencies = pluginUnderTestExternalDependencies.get()
            val projects = pluginUnderTestProjects.get()

            if (externalDependencies.isNotEmpty() || projects.isNotEmpty()) {
                val testPluginClasspath: Configuration =
                    configurations.create("testPluginClasspath")

                dependencies {
                    for (dependency in dependencies) {
                        testPluginClasspath(dependency)
                    }
                    for (dependency in externalDependencies) {
                        testPluginClasspath(dependency)
                    }
                    for (dependency in projects) {
                        testPluginClasspath(dependency)
                    }
                }

                tasks.withType<PluginUnderTestMetadata>().configureEach { metadata ->
                    metadata.pluginClasspath.from(testPluginClasspath)
                }
            }

            if (extendedGradle.get()) {
                forKotlinSetsDependencies(MAIN) {
                    implementation(gradleApi())
                    implementation(gradleKotlinDsl())
                    implementation(library(javiersc_gradle_extensions))
                }
                forKotlinSetsDependencies(TEST, TEST_FUNCTIONAL, TEST_INTEGRATION, TEST_FIXTURES) {
                    implementation(gradleTestKit())
                    implementation(library(javiersc_gradle_test_extensions))
                }
            }
        }

        configurable(priority = Priority.P4) {
            configure<GradlePluginDevelopmentExtension> {
                configure<SourceSetContainer> {
                    val sets: List<SourceSet> =
                        listOfNotNull(findByName(TEST_FUNCTIONAL), findByName(TEST_INTEGRATION))
                    testSourceSets.addAll(sets)
                }
            }
        }

        configurable(
            config = {
                configure<GradlePluginDevelopmentExtension> {
                    val gradlePluginDevelopmentExtension = this
                    gradlePluginDevelopmentExtension.website.set(hubdlePublishingMavenPom.url)
                    gradlePluginDevelopmentExtension.vcsUrl.set(hubdlePublishingMavenPom.scmUrl)
                }
            },
        )
    }
}

public interface HubdleGradlePluginDelegateFeatureExtension : BaseHubdleExtension {

    public val plugin: HubdleGradlePluginFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleGradlePluginFeatureExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleGradlePluginFeature: HubdleGradlePluginFeatureExtension
    get() = getHubdleExtension()
