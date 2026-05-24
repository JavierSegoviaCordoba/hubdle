@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem

import hubdle.ecosystem.feature.analysis.HubdleAnalysisFeature
import hubdle.ecosystem.feature.analysis.sonar.HubdleAnalysisSonarFeature
import hubdle.ecosystem.feature.documentation.HubdleDocumentationFeature
import hubdle.ecosystem.feature.documentation.readme.HubdleDocumentationReadmeFeature
import hubdle.ecosystem.feature.install.HubdleInstallFeature
import hubdle.ecosystem.feature.kotlin.HubdleKotlinFeature
import hubdle.ecosystem.feature.projectConfig.HubdleProjectConfigFeature
import hubdle.ecosystem.feature.versioning.HubdleVersioningFeature
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
    HubdleInstallFeature::class,
    HubdleKotlinFeature::class,
    HubdleVersioningFeature::class,
)
public open class HubdleEcosystemPlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        target.pluginManager.apply("com.javiersc.semver.features")
    }
}
