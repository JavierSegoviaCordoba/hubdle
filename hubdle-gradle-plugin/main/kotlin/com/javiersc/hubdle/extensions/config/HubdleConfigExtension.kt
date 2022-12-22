package com.javiersc.hubdle.extensions.config

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.config.analysis.HubdleConfigAnalysisExtension
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator.HubdleConfigBinaryCompatibilityValidatorExtension
import com.javiersc.hubdle.extensions.config.coverage.HubdleConfigCoverageExtension
import com.javiersc.hubdle.extensions.config.documentation.HubdleConfigDocumentationExtension
import com.javiersc.hubdle.extensions.config.format.HubdleConfigFormatExtension
import com.javiersc.hubdle.extensions.config.install.HubdleConfigInstallExtension
import com.javiersc.hubdle.extensions.config.language.settings.HubdleConfigLanguageSettingsExtension
import com.javiersc.hubdle.extensions.config.nexus.HubdleConfigNexusExtension
import com.javiersc.hubdle.extensions.config.publishing.HubdleConfigPublishingExtension
import com.javiersc.hubdle.extensions.config.versioning.HubdleConfigVersioningExtension
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

@HubdleDslMarker
public open class HubdleConfigExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    public val analysis: HubdleConfigAnalysisExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun analysis(action: Action<HubdleConfigAnalysisExtension> = Action {}) {
        analysis.enableAndExecute(action)
    }

    public val binaryCompatibilityValidator: HubdleConfigBinaryCompatibilityValidatorExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun binaryCompatibilityValidator(
        action: Action<HubdleConfigBinaryCompatibilityValidatorExtension> = Action {}
    ) {
        binaryCompatibilityValidator.enableAndExecute(action)
    }

    public val coverage: HubdleConfigCoverageExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun coverage(action: Action<HubdleConfigCoverageExtension> = Action {}) {
        coverage.enableAndExecute(action)
    }

    public val documentation: HubdleConfigDocumentationExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun documentation(action: Action<HubdleConfigDocumentationExtension> = Action {}) {
        documentation.enableAndExecute(action)
    }

    @HubdleDslMarker
    public fun explicitApi(explicitApiMode: ExplicitApiMode = ExplicitApiMode.Strict) {
        val isEnabled = property {
            isFullEnabled.get() && hubdleKotlinAny.any { it.isFullEnabled.get() }
        }
        configurable(isEnabled = isEnabled, priority = Priority.P4) {
            configure<KotlinProjectExtension> { explicitApi = explicitApiMode }
        }
    }

    public val format: HubdleConfigFormatExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun format(action: Action<HubdleConfigFormatExtension> = Action {}) {
        format.enableAndExecute(action)
    }

    public val languageSettings: HubdleConfigLanguageSettingsExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun languageSettings(action: Action<HubdleConfigLanguageSettingsExtension> = Action {}) {
        languageSettings.enableAndExecute(action)
    }

    public val install: HubdleConfigInstallExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun install(action: Action<HubdleConfigInstallExtension> = Action {}) {
        install.enableAndExecute(action)
    }

    public val nexus: HubdleConfigNexusExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun nexus(action: Action<HubdleConfigNexusExtension> = Action {}) {
        nexus.enableAndExecute(action)
    }

    public val publishing: HubdleConfigPublishingExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun publishing(action: Action<HubdleConfigPublishingExtension> = Action {}) {
        publishing.enableAndExecute(action)
    }

    public val versioning: HubdleConfigVersioningExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun versioning(action: Action<HubdleConfigVersioningExtension> = Action {}) {
        versioning.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleConfig: HubdleConfigExtension
    get() = getHubdleExtension()
