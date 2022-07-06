package com.javiersc.hubdle.extensions.config.publishing

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
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

    override var isEnabled: Boolean = IS_ENABLED

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        @HubdleDslMarker
        public fun Project.publishing(action: Action<MavenPublishingExtension>) {
            hubdleState.config.publishing.rawConfig.publishing = action
        }

        @HubdleDslMarker
        public fun Project.signing(action: Action<SigningExtension>) {
            hubdleState.config.publishing.rawConfig.signing = action
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
