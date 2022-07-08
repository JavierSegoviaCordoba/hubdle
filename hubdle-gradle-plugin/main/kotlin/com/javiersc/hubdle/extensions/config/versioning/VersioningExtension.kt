package com.javiersc.hubdle.extensions.config.versioning

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import com.javiersc.semver.gradle.plugin.SemverExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public abstract class VersioningExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<VersioningExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    public var tagPrefix: String = TAG_PREFIX

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.semver(action: Action<SemverExtension>) {
            hubdleState.config.versioning.rawConfig.semver = action
        }
    }

    public companion object {
        internal const val IS_ENABLED = true
        internal const val TAG_PREFIX = ""
    }
}
