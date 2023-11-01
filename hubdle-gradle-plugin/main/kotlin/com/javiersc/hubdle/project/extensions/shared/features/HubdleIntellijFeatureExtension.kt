package com.javiersc.hubdle.project.extensions.shared.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.shared.features.intellij.HubdleIntellijPluginFeatureExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleIntellijFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJava, hubdleKotlinJvm)

    public val plugin: HubdleIntellijPluginFeatureExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleIntellijPluginFeatureExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }
}

public interface HubdleIntellijDelegateFeatureExtension : BaseHubdleExtension {

    public val intellij: HubdleIntellijFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun intellij(action: Action<HubdleIntellijFeatureExtension> = Action {}) {
        intellij.enableAndExecute(action)
    }
}
