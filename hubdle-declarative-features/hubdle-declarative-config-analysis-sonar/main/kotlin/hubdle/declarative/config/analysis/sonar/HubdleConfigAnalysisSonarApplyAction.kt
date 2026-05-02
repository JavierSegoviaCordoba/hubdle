@file:Suppress("UnstableApiUsage", "ConstPropertyName")

package hubdle.declarative.config.analysis.sonar

import hubdle.declarative.config.analysis.HubdleConfigAnalysisDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.PluginIds
import hubdle.platform.applyPlugin
import hubdle.platform.configure
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction
import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarExtension

internal abstract class HubdleConfigAnalysisSonarApplyAction :
    ProjectFeatureApplyAction<
        HubdleConfigAnalysisSonarDefinition,
        HubdleConfigAnalysisSonarBuildModel,
        HubdleConfigAnalysisDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleConfigAnalysisSonarDefinition,
        buildModel: HubdleConfigAnalysisSonarBuildModel,
        parentDefinition: HubdleConfigAnalysisDefinition,
    ) = definition.run {
        val featureEffectiveEnabled =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }

            definition.configSonar()
        }
    }

    private fun HubdleConfigAnalysisSonarDefinition.configSonar() {
        applyPlugin(PluginIds.Sonarqube)

        configure<SonarExtension> {
            this.isSkipProject = false
            properties { properties ->
                fun prop(key: String, value: Any) = properties.property("sonar.$key", value)
                prop("host.url", hostUrl.orElse(DefaultHostUrl).get())
                prop("token", token.orElse("").get())
                prop("organization", organization.orElse("").get())
                prop("projectKey", projectKey.orElse("").get())
                prop("projectName", projectName.orElse("").get())
                for ((key, value) in properties.properties) {
                    if (key != "token" && key != "projectKey") {
                        logLifecycle { "Sonar property '$key' set to '$value'" }
                    }
                }
            }
        }
    }

    companion object {
        const val NAME: String = "sonar"
        const val DefaultHostUrl = "https://sonarcloud.io"
    }
}
