@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.config

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.analysis.HubdleAnalysisExtension
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator.HubdleBinaryCompatibilityValidatorExtension
import com.javiersc.hubdle.extensions.config.coverage.HubdleCoverageExtension
import com.javiersc.hubdle.extensions.config.documentation.HubdleDocumentationExtension
import com.javiersc.hubdle.extensions.config.format.HubdleFormatExtension
import com.javiersc.hubdle.extensions.config.install.HubdleInstallExtension
import com.javiersc.hubdle.extensions.config.language.settings.HubdleLanguageSettingsExtension
import com.javiersc.hubdle.extensions.config.nexus.HubdleNexusExtension
import com.javiersc.hubdle.extensions.config.publishing.HubdlePublishingExtension
import com.javiersc.hubdle.extensions.config.versioning.HubdleVersioningExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

@HubdleDslMarker
public open class HubdleConfigExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val analysis: HubdleAnalysisExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.analysis(action: Action<HubdleAnalysisExtension> = Action {}) {
        analysis.run { isEnabled = true }
        action.execute(analysis)
    }

    private val binaryCompatibilityValidator: HubdleBinaryCompatibilityValidatorExtension =
        objects.newInstance()

    @HubdleDslMarker
    public fun Project.binaryCompatibilityValidator() {
        binaryCompatibilityValidator.run { isEnabled = true }
    }

    private val coverage: HubdleCoverageExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.coverage(action: Action<HubdleCoverageExtension> = Action {}) {
        coverage.run { isEnabled = true }
        action.execute(coverage)
    }

    private val documentation: HubdleDocumentationExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.documentation(action: Action<in HubdleDocumentationExtension> = Action {}) {
        action.execute(documentation)
    }

    @HubdleDslMarker
    public fun Project.explicitApi(explicitApiMode: ExplicitApiMode = ExplicitApiMode.Strict) {
        hubdleState.config.explicitApiMode = explicitApiMode
    }

    private val format: HubdleFormatExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.format(action: Action<HubdleFormatExtension> = Action {}) {
        format.run { isEnabled = true }
        action.execute(format)
    }

    private val languageSettings: HubdleLanguageSettingsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.languageSettings(
        action: Action<HubdleLanguageSettingsExtension> = Action {}
    ) {
        action.execute(languageSettings)
    }

    private val install: HubdleInstallExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.install(action: Action<HubdleInstallExtension> = Action {}) {
        action.execute(install)
    }

    private val nexus: HubdleNexusExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.nexus(action: Action<HubdleNexusExtension> = Action {}) {
        nexus.run { isEnabled = true }
        action.execute(nexus)
    }

    private val publishing: HubdlePublishingExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.publishing(action: Action<HubdlePublishingExtension> = Action {}) {
        publishing.run { isEnabled = true }
        action.execute(publishing)
    }

    private val versioning: HubdleVersioningExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.versioning(action: Action<in HubdleVersioningExtension> = Action {}) {
        versioning.run { isEnabled = true }
        action.execute(versioning)
    }
}
