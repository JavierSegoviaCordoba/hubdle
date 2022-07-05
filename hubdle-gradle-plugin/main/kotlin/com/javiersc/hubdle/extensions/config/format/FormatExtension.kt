package com.javiersc.hubdle.extensions.config.format

import com.diffplug.gradle.spotless.SpotlessExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_FACEBOOK_KTFMT_VERSION
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class FormatExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<FormatExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABlED

    public val includes: MutableList<String> = INCLUDES

    public val excludes: MutableList<String> = EXCLUDES

    public var ktfmtVersion: String = KTFMT_VERSION

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        @HubdleDslMarker
        public fun Project.spotless(action: Action<SpotlessExtension>) {
            hubdleState.config.format.rawConfig.spotless = action
        }
    }

    public companion object {
        internal const val IS_ENABlED = true

        internal val INCLUDES: MutableList<String> = mutableListOf()

        internal val EXCLUDES: MutableList<String> = mutableListOf()

        internal const val KTFMT_VERSION = COM_FACEBOOK_KTFMT_VERSION
    }
}
