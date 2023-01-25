package com.javiersc.hubdle.extensions.shared.features.gradle

import com.gradle.publish.PluginBundleExtension
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.catalogDependency
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_GRADLE_GRADLE_EXTENSIONS_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.extensions.shared.HubdleGradleDependencies
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.SetProperty
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

    public val tags: SetProperty<String> = setProperty { emptySet() }

    @HubdleDslMarker
    public fun tags(vararg tags: String) {
        this.tags.addAll(tags.toList())
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
                forKotlinSetsDependencies("main") {
                    implementation(gradleApi())
                    implementation(gradleKotlinDsl())
                    implementation(catalogDependency(COM_JAVIERSC_GRADLE_GRADLE_EXTENSIONS_MODULE))
                }
                forKotlinSetsDependencies(
                    "test",
                    "testFunctional",
                    "testIntegration",
                    "testFixtures"
                ) {
                    implementation(gradleTestKit())
                    implementation(
                        catalogDependency(COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE)
                    )
                }
            }
        }

        configurable {
            configure<PluginBundleExtension> {
                tags = this@HubdleGradlePluginFeatureExtension.tags.get()
                website = project.getProperty(HubdleProperty.POM.url)
                vcsUrl = project.getProperty(HubdleProperty.POM.scmUrl)
            }
        }
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
