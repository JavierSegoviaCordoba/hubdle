package com.javiersc.hubdle.extensions.kotlin.tools.publishing

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension as MavenPublishingExtension
import org.gradle.kotlin.dsl.the
import org.gradle.plugins.signing.SigningExtension

@HubdleDslMarker
public open class PublishingExtension :
    EnableableOptions, RawConfigOptions<PublishingExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { action.execute(RawConfigExtension()) }
    }

    public open class RawConfigExtension {
        public fun Project.publishing(action: Action<MavenPublishingExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }

        public fun Project.signing(action: Action<SigningExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
