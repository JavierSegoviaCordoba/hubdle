package com.javiersc.hubdle.project.extensions.config.versioning

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.config.versioning.semver.HubdleConfigVersioningSemverExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public abstract class HubdleConfigVersioningExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    public val semver: HubdleConfigVersioningSemverExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun semver(action: Action<HubdleConfigVersioningSemverExtension> = Action {}) {
        semver.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleVersioning: HubdleConfigVersioningExtension
    get() = getHubdleExtension()
