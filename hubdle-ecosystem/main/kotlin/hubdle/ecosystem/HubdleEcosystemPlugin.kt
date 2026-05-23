@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem

import hubdle.ecosystem.feature.analysis.HubdleAnalysisFeature
import hubdle.ecosystem.feature.analysis.sonar.HubdleAnalysisSonarFeature
import hubdle.ecosystem.feature.documentation.HubdleDocumentationFeature
import hubdle.declarative.documentation.readme.HubdleDocumentationReadmeFeature
import hubdle.declarative.documentation.readme.badges.HubdleDocumentationReadmeBadgesFeature
import hubdle.declarative.kotlin.HubdleKotlinFeature
import hubdle.declarative.versioning.HubdleVersioningFeature
import hubdle.declarative.versioning.semver.HubdleVersioningSemverFeature
import hubdle.ecosystem.feature.projectConfig.HubdleProjectConfigFeature
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.features.annotations.RegistersProjectFeatures

@RegistersProjectFeatures(
    HubdleProjectType::class,
    HubdleProjectConfigFeature::class,
    HubdleAnalysisFeature::class,
    HubdleAnalysisSonarFeature::class,
    HubdleDocumentationFeature::class,
    HubdleDocumentationReadmeFeature::class,
    HubdleDocumentationReadmeBadgesFeature::class,
    HubdleKotlinFeature::class,
    HubdleVersioningFeature::class,
    HubdleVersioningSemverFeature::class,
)
public open class HubdleEcosystemPlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        // NO-OP
    }
}
