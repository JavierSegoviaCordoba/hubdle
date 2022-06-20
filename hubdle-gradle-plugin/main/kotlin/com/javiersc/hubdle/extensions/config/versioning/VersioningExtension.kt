package com.javiersc.hubdle.extensions.config.versioning

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import com.javiersc.semver.gradle.plugin.SemverExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

@HubdleDslMarker
public abstract class VersioningExtension :
    EnableableOptions, RawConfigOptions<VersioningExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    public var tagPrefix: String = TAG_PREFIX

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { action.execute(the()) }
    }

    public open class RawConfigExtension {
        public fun Project.semver(action: Action<SemverExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }
    }

    public companion object {
        internal const val IS_ENABLED = true
        internal const val TAG_PREFIX = ""
    }
}
