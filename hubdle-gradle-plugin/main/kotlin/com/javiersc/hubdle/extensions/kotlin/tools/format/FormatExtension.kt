package com.javiersc.hubdle.extensions.kotlin.tools.format

import com.diffplug.gradle.spotless.SpotlessExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.dependencies._internal.COM_FACEBOOK_KTFMT_VERSION
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

@HubdleDslMarker
public open class FormatExtension :
    EnableableOptions, RawConfigOptions<FormatExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABlED

    public val includes: MutableList<String> =
        mutableListOf(
            "*/kotlin/**/*.kt",
            "src/*/kotlin/**/*.kt",
        )

    public val excludes: MutableList<String> =
        mutableListOf(
            "*/resources/**/*.kt",
            "src/*/resources/**/*.kt",
            "**/build/**",
            "**/.gradle/**",
        )

    public var ktfmtVersion: String = KTFMT_VERSION

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { RawConfigExtension() }
    }

    public open class RawConfigExtension {
        public fun Project.spotless(action: Action<SpotlessExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }
    }

    public companion object {
        internal const val IS_ENABlED = true
        internal const val KTFMT_VERSION = COM_FACEBOOK_KTFMT_VERSION
    }
}
