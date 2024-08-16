package com.javiersc.hubdle.project.extensions.kotlin.android.library

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.configurableDependencies
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.publishing.maven.configurableMavenPublishing
import com.javiersc.hubdle.project.extensions.kotlin._internal.configurableSrcDirs
import com.javiersc.hubdle.project.extensions.kotlin.android._internal.configureAndroidLibraryJavaVersion
import com.javiersc.hubdle.project.extensions.kotlin.android.hubdleAndroid
import com.javiersc.hubdle.project.extensions.kotlin.android.library.features.HubdleKotlinAndroidLibraryFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.javiersc.hubdle.project.extensions.shared.android.HubdleAndroidDelegateSharedApis
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure

@HubdleDslMarker
public open class HubdleKotlinAndroidLibraryExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project), HubdleAndroidDelegateSharedApis {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAndroid)

    public val features: HubdleKotlinAndroidLibraryFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun features(action: Action<HubdleKotlinAndroidLibraryFeaturesExtension>) {
        features.enableAndExecute(action)
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
    public fun android(action: Action<LibraryExtension>): Unit = fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugins()

        lazyConfigurable {
            configure<LibraryExtension> {
                compileSdk = hubdleAndroid.compileSdk.get()
                defaultConfig.minSdk = hubdleAndroid.minSdk.get()
                namespace = hubdleAndroid.namespace.orNull
            }
            configureAndroidLibraryJavaVersion()
        }

        configurableSrcDirs()
        configurableDependencies()
        configurePublishing()
    }

    private fun applicablePlugins() {
        applicablePlugin(
            scope = Scope.CurrentProject,
            pluginId = PluginId.AndroidLibrary,
        )

        applicablePlugin(
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
