@file:Suppress("UnstableApiUsage")

package hubdle.declarative

import hubdle.declarative.analysis.HubdleAnalysisFeature
import hubdle.declarative.analysis.sonar.HubdleAnalysisSonarFeature
import hubdle.declarative.documentation.HubdleDocumentationFeature
import hubdle.declarative.documentation.readme.HubdleDocumentationReadmeFeature
import hubdle.declarative.documentation.readme.badges.HubdleDocumentationReadmeBadgesFeature
import hubdle.declarative.kotlin.HubdleKotlinFeature
import hubdle.declarative.projectConfig.HubdleProjectConfigFeature
import hubdle.declarative.versioning.HubdleVersioningFeature
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
)
public open class HubdleDeclarativePlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        // NO-OP
    }
}
