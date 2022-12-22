package com.javiersc.hubdle.extensions.kotlin.android.library

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.configDefaultAndroidSourceSets
import com.javiersc.hubdle.extensions._internal.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions._internal.configureDependencies
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.publishing._internal.configurableMavenPublishing
import com.javiersc.hubdle.extensions.kotlin.android._internal.calculateAndroidNamespace
import com.javiersc.hubdle.extensions.kotlin.android.hubdleAndroid
import com.javiersc.hubdle.extensions.kotlin.android.library.features.HubdleKotlinAndroidLibraryFeaturesExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinAndroidLibraryExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAndroid)

    override val priority: Priority = Priority.P3

    public val features: HubdleKotlinAndroidLibraryFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun configuration(name: String, action: Action<Configuration>) {
        userConfigurable {
            configure<LibraryExtension> { project.configurations.named(name, action::execute) }
        }
    }

    @HubdleDslMarker
    public fun sourceSet(name: String, action: Action<KotlinSourceSet>) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named(name, action::execute) }
        }
    }

    @HubdleDslMarker
    public fun main(action: Action<KotlinSourceSet>) {
        sourceSet("main", action)
    }

    @HubdleDslMarker
    public fun test(action: Action<KotlinSourceSet>) {
        sourceSet("test", action)
    }

    // TODO: improve and enable using this docs:
    //  https://developer.android.com/studio/publish-library/configure-pub-variants
    // @HubdleDslMarker
    // public fun publishLibraryVariants(vararg names: String) {
    //
    // }
    //
    // @HubdleDslMarker
    // public fun publishAllLibraryVariants(enable: Boolean = true) {
    //
    // }

    @HubdleDslMarker
    public fun android(action: Action<LibraryExtension>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugins()

        configurable {
            configureDependencies()
            configureDefaultKotlinSourceSets()

            configure<LibraryExtension> {
                compileSdk = hubdleAndroid.compileSdk.get()
                defaultConfig.minSdk = hubdleAndroid.minSdk.get()
                namespace = calculateAndroidNamespace(hubdleAndroid.namespace.orNull)

                sourceSets.all { set -> set.configDefaultAndroidSourceSets() }
            }
        }
        configurePublishing()
    }

    private fun applicablePlugins() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.AndroidLibrary,
        )

        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinAndroid,
        )
    }

    private fun configurePublishing() {
        configurableMavenPublishing(mavenPublicationName = "release") {
            configure<LibraryExtension> {
                publishing {
                    multipleVariants {
                        withSourcesJar()
                        withJavadocJar()
                        allVariants()
                    }
                }
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleAndroidLibrary: HubdleKotlinAndroidLibraryExtension
    get() = getHubdleExtension()

internal val Project.hubdleAndroidLibrary: HubdleKotlinAndroidLibraryExtension
    get() = getHubdleExtension()
