package com.javiersc.hubdle.extensions.config._internal

import com.javiersc.hubdle.extensions._internal.HubdleState
import com.javiersc.hubdle.extensions.config.HubdleConfigExtension
import com.javiersc.hubdle.extensions.config.analysis.HubdleConfigAnalysisExtension
import com.javiersc.hubdle.extensions.config.analysis.reports.HubdleConfigAnalysisReportsExtension
import com.javiersc.hubdle.extensions.config.binary.compatibility.validator.HubdleConfigBinaryCompatibilityValidatorExtension
import com.javiersc.hubdle.extensions.config.coverage.HubdleConfigCoverageExtension
import com.javiersc.hubdle.extensions.config.documentation.HubdleConfigDocumentationExtension
import com.javiersc.hubdle.extensions.config.documentation.api.HubdleConfigDocumentationApiExtension
import com.javiersc.hubdle.extensions.config.documentation.changelog.HubdleConfigDocumentationChangelogExtension
import com.javiersc.hubdle.extensions.config.documentation.readme.HubdleConfigDocumentationReadmeExtension
import com.javiersc.hubdle.extensions.config.documentation.readme.badges.HubdleConfigDocumentationReadmeBadgesExtension
import com.javiersc.hubdle.extensions.config.documentation.site.HubdleConfigDocumentationSiteExtension
import com.javiersc.hubdle.extensions.config.documentation.site.reports.HubdleConfigDocumentationSiteReportsExtension
import com.javiersc.hubdle.extensions.config.format.HubdleConfigFormatExtension
import com.javiersc.hubdle.extensions.config.install.HubdleConfigInstallExtension
import com.javiersc.hubdle.extensions.config.install.pre.commits.HubdleConfigInstallPreCommitsExtension
import com.javiersc.hubdle.extensions.config.language.settings.HubdleConfigLanguageSettingsExtension
import com.javiersc.hubdle.extensions.config.nexus.HubdleConfigNexusExtension
import com.javiersc.hubdle.extensions.config.publishing.HubdleConfigPublishingExtension
import com.javiersc.hubdle.extensions.config.testing.HubdleConfigTestingExtension
import com.javiersc.hubdle.extensions.config.versioning.HubdleConfigVersioningExtension

internal fun HubdleState.createHubdleConfigExtensions() {
    createExtension<HubdleConfigExtension> {
        configureAnalysisExtensions()
        createExtension<HubdleConfigBinaryCompatibilityValidatorExtension>()
        createExtension<HubdleConfigCoverageExtension>()
        configureDocumentationExtensions()
        createExtension<HubdleConfigFormatExtension>()
        createExtension<HubdleConfigInstallExtension> {
            createExtension<HubdleConfigInstallPreCommitsExtension>()
        }
        createExtension<HubdleConfigLanguageSettingsExtension>()
        createExtension<HubdleConfigNexusExtension>()
        createExtension<HubdleConfigPublishingExtension>()
        createExtension<HubdleConfigTestingExtension>()
        createExtension<HubdleConfigVersioningExtension>()
    }
}

private fun HubdleState.configureAnalysisExtensions() {
    createExtension<HubdleConfigAnalysisExtension> {
        createExtension<HubdleConfigAnalysisReportsExtension>()
    }
}

private fun HubdleState.configureDocumentationExtensions() {
    createExtension<HubdleConfigDocumentationExtension> {
        createExtension<HubdleConfigDocumentationApiExtension>()
        createExtension<HubdleConfigDocumentationChangelogExtension>()
        createExtension<HubdleConfigDocumentationReadmeExtension> {
            createExtension<HubdleConfigDocumentationReadmeBadgesExtension>()
        }
        createExtension<HubdleConfigDocumentationSiteExtension> {
            createExtension<HubdleConfigDocumentationSiteReportsExtension>()
        }
    }
}
