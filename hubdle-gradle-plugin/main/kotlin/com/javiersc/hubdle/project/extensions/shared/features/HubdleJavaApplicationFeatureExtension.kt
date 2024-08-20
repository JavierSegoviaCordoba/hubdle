package com.javiersc.hubdle.project.extensions.shared.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.JavaApplication
import org.gradle.api.provider.Property

public open class HubdleJavaApplicationFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJava, hubdleKotlinJvm)

    public val mainClass: Property<String?> = property { null }

    @HubdleDslMarker
    public fun application(action: Action<JavaApplication> = Action {}) {
        lazyConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.GradleApplication)

        lazyConfigurable { the<JavaApplication>().mainClass.set(mainClass) }
    }
}

public interface HubdleJavaApplicationDelegateFeatureExtension : BaseHubdleExtension {

    public val application: HubdleJavaApplicationFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun application(action: Action<HubdleJavaApplicationFeatureExtension> = Action {}) {
        application.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleJavaApplicationFeature:
    HubdleJavaApplicationFeatureExtension
    get() = getHubdleExtension()
