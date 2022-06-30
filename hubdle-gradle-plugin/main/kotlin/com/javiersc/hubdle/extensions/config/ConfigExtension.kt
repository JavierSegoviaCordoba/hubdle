@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.config

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.analysis.AnalysisExtension
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator.BinaryCompatibilityValidatorExtension
import com.javiersc.hubdle.extensions.config.coverage.CoverageExtension
import com.javiersc.hubdle.extensions.config.documentation.DocumentationExtension
import com.javiersc.hubdle.extensions.config.format.FormatExtension
import com.javiersc.hubdle.extensions.config.install.InstallExtension
import com.javiersc.hubdle.extensions.config.language.settings.LanguageSettingsExtension
import com.javiersc.hubdle.extensions.config.nexus.NexusExtension
import com.javiersc.hubdle.extensions.config.publishing.PublishingExtension
import com.javiersc.hubdle.extensions.config.versioning.VersioningExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

@HubdleDslMarker
public open class ConfigExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val analysis: AnalysisExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.analysis(action: Action<AnalysisExtension> = Action {}) {
        analysis.isEnabled = true
        action.execute(analysis)
        hubdleState.config.analysis.isEnabled = analysis.isEnabled
        hubdleState.config.analysis.includes += analysis.includes
        hubdleState.config.analysis.excludes += analysis.excludes
    }

    private val binaryCompatibilityValidator: BinaryCompatibilityValidatorExtension =
        objects.newInstance()

    @HubdleDslMarker
    public fun Project.binaryCompatibilityValidator() {
        binaryCompatibilityValidator.isEnabled = true
        hubdleState.config.binaryCompatibilityValidator.isEnabled =
            binaryCompatibilityValidator.isEnabled
    }

    private val coverage: CoverageExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.coverage(action: Action<CoverageExtension> = Action {}) {
        coverage.isEnabled = true
        action.execute(coverage)
        hubdleState.config.coverage.isEnabled = coverage.isEnabled
    }

    private val documentation: DocumentationExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.documentation(action: Action<in DocumentationExtension> = Action {}) {
        action.execute(documentation)
    }

    @HubdleDslMarker
    public fun Project.explicitApi(explicitApiMode: ExplicitApiMode = ExplicitApiMode.Strict) {
        hubdleState.config.explicitApiMode = explicitApiMode
    }

    private val format: FormatExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.format(action: Action<FormatExtension> = Action {}) {
        format.isEnabled = true
        action.execute(format)
        hubdleState.config.format.isEnabled = format.isEnabled
        hubdleState.config.format.includes += format.includes
        hubdleState.config.format.excludes += format.excludes
        hubdleState.config.format.ktfmtVersion = format.ktfmtVersion
    }

    private val languageSettings: LanguageSettingsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.languageSettings(action: Action<LanguageSettingsExtension> = Action {}) {
        action.execute(languageSettings)
    }

    private val install: InstallExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.install(action: Action<InstallExtension> = Action {}) {
        action.execute(install)
    }

    private val nexus: NexusExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.nexus(action: Action<NexusExtension> = Action {}) {
        nexus.isEnabled = true
        action.execute(nexus)
        hubdleState.config.nexus.isEnabled = nexus.isEnabled
    }

    private val publishing: PublishingExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.publishing(action: Action<PublishingExtension> = Action {}) {
        publishing.isEnabled = true
        action.execute(publishing)
        hubdleState.config.publishing.isEnabled = publishing.isEnabled
    }

    private val versioning: VersioningExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.versioning(action: Action<in VersioningExtension> = Action {}) {
        versioning.isEnabled = true
        action.execute(versioning)
        hubdleState.config.versioning.isEnabled = versioning.isEnabled
        hubdleState.config.versioning.tagPrefix = versioning.tagPrefix
    }
}
