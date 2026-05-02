@file:Suppress("UnstableApiUsage")

package hubdle.declarative.kotlin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleKotlinFeature::class)
public open class HubdleKotlinFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(HubdleKotlinApplyAction.NAME, HubdleKotlinApplyAction::class)
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
