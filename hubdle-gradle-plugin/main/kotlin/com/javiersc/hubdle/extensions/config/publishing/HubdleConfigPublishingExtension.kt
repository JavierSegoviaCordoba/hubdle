package com.javiersc.hubdle.extensions.config.publishing

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.hubdleConfig
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension as GradlePublishingExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.plugins.signing.SigningExtension

@HubdleDslMarker
public open class HubdleConfigPublishingExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    @HubdleDslMarker
    public fun repositories(action: Action<RepositoryHandler> = Action {}) {
        userConfigurable { action.execute(the<PublishingExtension>().repositories) }
    }

    @HubdleDslMarker
    public fun publishing(action: Action<GradlePublishingExtension> = Action {}) {
        userConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun signing(action: Action<SigningExtension> = Action {}) {
        userConfigurable { action.execute(the()) }
    }
}

internal val HubdleEnableableExtension.hubdlePublishing: HubdleConfigPublishingExtension
    get() = getHubdleExtension()
