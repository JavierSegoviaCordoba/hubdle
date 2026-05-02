@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleConfigFeature::class)
public open class HubdleConfigFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(HubdleConfigApplyAction.NAME, HubdleConfigApplyAction::class)
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
