@file:Suppress("UnstableApiUsage")

package hubdle.declarative.analysis.sonar

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleAnalysisSonarFeature::class)
public open class HubdleAnalysisSonarFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(
                HubdleAnalysisSonarApplyAction.NAME,
                HubdleAnalysisSonarApplyAction::class,
            )
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
