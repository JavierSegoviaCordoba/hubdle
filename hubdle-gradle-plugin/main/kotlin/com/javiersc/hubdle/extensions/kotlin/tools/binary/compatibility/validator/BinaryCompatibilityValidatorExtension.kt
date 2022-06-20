package com.javiersc.hubdle.extensions.kotlin.tools.binary.compatibility.validator

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import kotlinx.kover.api.KoverExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

@HubdleDslMarker
public open class BinaryCompatibilityValidatorExtension :
    EnableableOptions, RawConfigOptions<BinaryCompatibilityValidatorExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { RawConfigExtension() }
    }

    public open class RawConfigExtension {
        public fun Project.kover(action: Action<KoverExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
