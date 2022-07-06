package com.javiersc.hubdle.extensions.config.binary.compatibility.validator

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class BinaryCompatibilityValidatorExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<BinaryCompatibilityValidatorExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        @HubdleDslMarker
        public fun Project.apiValidation(action: Action<ApiValidationExtension>) {
            hubdleState.config.binaryCompatibilityValidator.rawConfig.apiValidation = action
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
