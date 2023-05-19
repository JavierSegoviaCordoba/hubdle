package com.javiersc.hubdle.project.extensions.kotlin.android.application

import com.android.build.api.dsl.ApplicationExtension
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.configurableDependencies
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin._internal.configurableSrcDirs
import com.javiersc.hubdle.project.extensions.kotlin.android._internal.configureAndroidApplicationJavaVersion
import com.javiersc.hubdle.project.extensions.kotlin.android.application.features.HubdleKotlinAndroidApplicationFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.hubdleAndroid
import com.javiersc.hubdle.project.extensions.kotlin.shared.HubdleKotlinMinimalSourceSetConfigurableExtension
import com.javiersc.hubdle.project.extensions.shared.android.HubdleAndroidDelegateSharedApis
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure

@HubdleDslMarker
public open class HubdleKotlinAndroidApplicationExtension
@Inject
constructor(
    project: Project,
) : HubdleKotlinMinimalSourceSetConfigurableExtension(project), HubdleAndroidDelegateSharedApis {

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
                namespace = hubdleAndroid.namespace.orNull
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
