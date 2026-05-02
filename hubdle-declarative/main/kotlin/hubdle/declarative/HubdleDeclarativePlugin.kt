@file:Suppress("UnstableApiUsage")

package hubdle.declarative

import hubdle.declarative.config.HubdleConfigFeature
import hubdle.declarative.config.analysis.HubdleConfigAnalysisFeature
import hubdle.declarative.config.analysis.sonar.HubdleConfigAnalysisSonarFeature
import hubdle.declarative.config.documentation.HubdleConfigDocumentationFeature
import hubdle.declarative.config.documentation.readme.HubdleConfigDocumentationReadmeFeature
import hubdle.declarative.config.documentation.readme.badges.HubdleConfigDocumentationReadmeBadgesFeature
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.features.annotations.RegistersProjectFeatures

@RegistersProjectFeatures(
    HubdleProjectType::class,
    HubdleConfigFeature::class,
    HubdleConfigAnalysisFeature::class,
    HubdleConfigAnalysisSonarFeature::class,
    HubdleConfigDocumentationFeature::class,
    HubdleConfigDocumentationReadmeFeature::class,
    HubdleConfigDocumentationReadmeBadgesFeature::class,
)
public open class HubdleDeclarativePlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        // NO-OP
    }
}
