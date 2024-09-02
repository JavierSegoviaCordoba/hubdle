package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleKotlinBuildKonfigFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    @HubdleDslMarker
    public fun buildkonfig(action: Action<BuildKonfigExtension> = Action {}): Unit =
        fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.CodingfelineBuildkonfig)
    }
}

public interface HubdleKotlinBuildKonfigDelegateFeatureExtension : BaseHubdleExtension {

    public val buildkonfig: HubdleKotlinBuildKonfigFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun buildkonfig(action: Action<HubdleKotlinBuildKonfigFeatureExtension> = Action {}) {
        buildkonfig.enableAndExecute(action)
    }
}
