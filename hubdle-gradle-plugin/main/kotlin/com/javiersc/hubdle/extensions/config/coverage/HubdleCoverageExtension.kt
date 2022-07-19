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
public open class HubdleCoverageExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<HubdleCoverageExtension.RawConfigExtension> {

    override var Project.isEnabled: Boolean
        get() = hubdleState.config.coverage.isEnabled
        set(value) = hubdleState.config.coverage.run { isEnabled = value }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.kover(action: Action<KoverExtension>) {
            hubdleState.config.coverage.rawConfig.kover = action
        }
    }
}
