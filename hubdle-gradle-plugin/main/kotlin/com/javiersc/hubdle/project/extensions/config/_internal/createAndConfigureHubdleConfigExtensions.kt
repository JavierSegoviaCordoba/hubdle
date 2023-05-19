package com.javiersc.hubdle.project.extensions.config._internal

import com.javiersc.hubdle.project.extensions._internal.HubdleState
import com.javiersc.hubdle.project.extensions.config.HubdleConfigExtension
import com.javiersc.hubdle.project.extensions.config.analysis.HubdleConfigAnalysisExtension
import com.javiersc.hubdle.project.extensions.config.analysis.reports.HubdleConfigAnalysisReportsExtension
import com.javiersc.hubdle.project.extensions.config.analysis.tools.HubdleConfigAnalysisDetektExtension
import com.javiersc.hubdle.project.extensions.config.analysis.tools.HubdleConfigAnalysisSonarExtension
import com.javiersc.hubdle.project.extensions.config.binary.compatibility.validator.HubdleConfigBinaryCompatibilityValidatorExtension
import com.javiersc.hubdle.project.extensions.config.coverage.HubdleConfigCoverageExtension
import com.javiersc.hubdle.project.extensions.config.documentation.HubdleConfigDocumentationExtension
import com.javiersc.hubdle.project.extensions.config.documentation.api.HubdleConfigDocumentationApiExtension
import com.javiersc.hubdle.project.extensions.config.documentation.changelog.HubdleConfigDocumentationChangelogExtension
import com.javiersc.hubdle.project.extensions.config.documentation.readme.HubdleConfigDocumentationReadmeExtension
import com.javiersc.hubdle.project.extensions.config.documentation.readme.badges.HubdleConfigDocumentationReadmeBadgesExtension
import com.javiersc.hubdle.project.extensions.config.documentation.site.HubdleConfigDocumentationSiteExtension
import com.javiersc.hubdle.project.extensions.config.documentation.site.analysis.HubdleConfigDocumentationSiteAnalysisExtension
import com.javiersc.hubdle.project.extensions.config.format.HubdleConfigFormatExtension
import com.javiersc.hubdle.project.extensions.config.install.HubdleConfigInstallExtension
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.HubdleConfigInstallPreCommitsExtension
import com.javiersc.hubdle.project.extensions.config.language.settings.HubdleConfigLanguageSettingsExtension
import com.javiersc.hubdle.project.extensions.config.nexus.HubdleConfigNexusExtension
import com.javiersc.hubdle.project.extensions.config.publishing.HubdleConfigPublishingExtension
import com.javiersc.hubdle.project.extensions.config.publishing.maven.HubdleConfigPublishingMavenExtension
import com.javiersc.hubdle.project.extensions.config.publishing.maven.HubdleConfigPublishingMavenPomExtension
import com.javiersc.hubdle.project.extensions.config.publishing.signing.HubdleConfigPublishingSigningExtension
import com.javiersc.hubdle.project.extensions.config.testing.HubdleConfigTestingExtension
import com.javiersc.hubdle.project.extensions.config.versioning.HubdleConfigVersioningExtension
import com.javiersc.hubdle.project.extensions.config.versioning.semver.HubdleConfigVersioningSemverExtension

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
        createExtension<HubdleConfigPublishingExtension> {
            createExtension<HubdleConfigPublishingMavenExtension> {
                createExtension<HubdleConfigPublishingMavenPomExtension>()
            }
            createExtension<HubdleConfigPublishingSigningExtension>()
        }
        createExtension<HubdleConfigTestingExtension>()
        createExtension<HubdleConfigVersioningExtension> {
            createExtension<HubdleConfigVersioningSemverExtension>()
        }
    }
}

private fun HubdleState.configureAnalysisExtensions() {
    createExtension<HubdleConfigAnalysisExtension> {
        createExtension<HubdleConfigAnalysisDetektExtension>()
        createExtension<HubdleConfigAnalysisSonarExtension>()
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
            createExtension<HubdleConfigDocumentationSiteAnalysisExtension>()
        }
    }
}
