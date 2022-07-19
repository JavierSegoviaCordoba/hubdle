package com.javiersc.hubdle.extensions.config.publishing

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.model.ObjectFactory
import org.gradle.api.publish.PublishingExtension as MavenPublishingExtension
import org.gradle.kotlin.dsl.newInstance
import org.gradle.plugins.signing.SigningExtension

@HubdleDslMarker
public open class PublishingExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<PublishingExtension.RawConfigExtension> {

    override var Project.isEnabled: Boolean
        get() = hubdleState.config.publishing.isEnabled
        set(value) = hubdleState.config.publishing.run { isEnabled = value }

    @HubdleDslMarker
    public fun Project.repositories(action: Action<RepositoryHandler> = Action {}) {
        hubdleState.config.publishing.repositories = action
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.publishing(action: Action<MavenPublishingExtension>) {
            hubdleState.config.publishing.rawConfig.publishing = action
        }

        public fun Project.signing(action: Action<SigningExtension>) {
            hubdleState.config.publishing.rawConfig.signing = action
        }
    }
}
