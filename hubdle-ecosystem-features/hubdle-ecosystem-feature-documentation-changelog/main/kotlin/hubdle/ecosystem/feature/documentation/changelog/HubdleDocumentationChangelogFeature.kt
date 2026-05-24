@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation.changelog

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleDocumentationChangelogFeature::class)
public open class HubdleDocumentationChangelogFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(
                HubdleDocumentationChangelogApplyAction.NAME,
                HubdleDocumentationChangelogApplyAction::class,
            )
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
