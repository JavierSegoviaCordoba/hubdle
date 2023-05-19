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
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.publishing.maven.hubdlePublishingMavenPom
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_gradle_gradleExtensions
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_gradle_gradleTestExtensions
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.shared.HubdleGradleDependencies
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
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
        get() = setOf(hubdleKotlinJvm)

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

    @HubdleDslMarker
    public fun pluginUnderTestDependencies(
        vararg pluginUnderTestDependencies: Provider<MinimalExternalModuleDependency>
    ) {
        this.pluginUnderTestDependencies.addAll(
            provider { pluginUnderTestDependencies.map { it.get() } }
        )
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JavaGradlePlugin
        )

        applicablePlugin(
            isEnabled = property { isFullEnabled.get() && hubdlePublishing.isFullEnabled.get() },
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.GradlePluginPublish
        )

        configurable {
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

            if (extendedGradle.get()) {
                forKotlinSetsDependencies(MAIN) {
                    implementation(gradleApi())
                    implementation(gradleKotlinDsl())
                    implementation(library(javiersc_gradle_gradleExtensions))
                }
                forKotlinSetsDependencies(TEST, TEST_FUNCTIONAL, TEST_INTEGRATION, TEST_FIXTURES) {
                    implementation(gradleTestKit())
                    implementation(library(javiersc_gradle_gradleTestExtensions))
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
            }
        )
    }
}

public interface HubdleGradlePluginDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val plugin: HubdleGradlePluginFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleGradlePluginFeatureExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleGradlePluginFeature: HubdleGradlePluginFeatureExtension
    get() = getHubdleExtension()
