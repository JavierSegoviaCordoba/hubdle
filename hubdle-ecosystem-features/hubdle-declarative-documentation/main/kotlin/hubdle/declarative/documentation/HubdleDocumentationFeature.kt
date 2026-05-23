@file:Suppress("UnstableApiUsage")

package hubdle.declarative.documentation

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleDocumentationFeature::class)
public open class HubdleDocumentationFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(
                HubdleDocumentationApplyAction.NAME,
                HubdleDocumentationApplyAction::class,
            )
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
