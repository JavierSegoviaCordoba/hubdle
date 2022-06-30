package com.javiersc.hubdle.extensions.config.coverage

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import kotlinx.kover.api.KoverExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class CoverageExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<CoverageExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        @HubdleDslMarker
        public fun Project.kover(action: Action<KoverExtension>) {
            hubdleState.config.coverage.rawConfig.kover = action
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
