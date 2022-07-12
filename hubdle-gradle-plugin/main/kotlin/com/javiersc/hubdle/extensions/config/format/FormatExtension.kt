package com.javiersc.hubdle.extensions.config.format

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
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

    override var Project.isEnabled: Boolean
        get() = hubdleState.config.format.isEnabled
        set(value) = hubdleState.config.format.run { isEnabled = value }

    public fun Project.includes(vararg includes: String) {
        hubdleState.config.format.includes += includes
    }

    public fun Project.excludes(vararg excludes: String) {
        hubdleState.config.format.excludes += excludes
    }

    public var Project.ktfmtVersion: String
        get() = hubdleState.config.format.ktfmtVersion
        set(value) = hubdleState.config.format.run { ktfmtVersion = value }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.spotless(action: Action<SpotlessExtension>) {
            hubdleState.config.format.rawConfig.spotless = action
        }

        public fun Project.spotlessPredeclare(action: Action<SpotlessExtensionPredeclare>) {
            hubdleState.config.format.rawConfig.spotlessPredeclare = action
        }
    }
}
