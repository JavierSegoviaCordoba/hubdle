package com.javiersc.hubdle.extensions.kotlin.android.application

import com.android.build.api.dsl.ApplicationExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.configurableDependencies
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin._internal.configurableSrcDirs
import com.javiersc.hubdle.extensions.kotlin.android._internal.calculateAndroidNamespace
import com.javiersc.hubdle.extensions.kotlin.android._internal.configureAndroidApplicationJavaVersion
import com.javiersc.hubdle.extensions.kotlin.android.application.features.HubdleKotlinAndroidApplicationFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.android.hubdleAndroid
import com.javiersc.hubdle.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinAndroidApplicationExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAndroid)

    public val features: HubdleKotlinAndroidApplicationFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun features(action: Action<HubdleKotlinAndroidApplicationFeaturesExtension>) {
        features.enableAndExecute(action)
    }

    public val applicationId: Property<String?> = property { hubdleAndroid.namespace.get() }

    public val versionCode: Property<Int> = property { 1 }

    public val versionName: Property<String?> = property { "0.1.0" }

    @HubdleDslMarker
    public fun configuration(name: String, action: Action<Configuration>) {
        userConfigurable {
            configure<ApplicationExtension> { project.configurations.named(name, action::execute) }
        }
    }

    @HubdleDslMarker
    public fun sourceSet(name: String, action: Action<KotlinSourceSet>) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named(name, action::execute) }
        }
    }

    @HubdleDslMarker
    public fun android(action: Action<ApplicationExtension>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        val application = hubdleAndroidApplication
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.AndroidApplication,
        )

        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinAndroid,
        )

        configurable {
            configure<ApplicationExtension> {
                defaultConfig.applicationId = application.applicationId.get()
                defaultConfig.versionCode = application.versionCode.get()
                defaultConfig.versionName = application.versionName.get()
                compileSdk = hubdleAndroid.compileSdk.get()
                defaultConfig.minSdk = hubdleAndroid.minSdk.get()
                namespace = project.calculateAndroidNamespace(hubdleAndroid.namespace.orNull)
            }
            configureAndroidApplicationJavaVersion()
        }
        configurableSrcDirs()
        configurableDependencies()
    }
}

internal val HubdleEnableableExtension.hubdleAndroidApplication:
    HubdleKotlinAndroidApplicationExtension
    get() = getHubdleExtension()

internal val Project.hubdleAndroidApplication: HubdleKotlinAndroidApplicationExtension
    get() = getHubdleExtension()
