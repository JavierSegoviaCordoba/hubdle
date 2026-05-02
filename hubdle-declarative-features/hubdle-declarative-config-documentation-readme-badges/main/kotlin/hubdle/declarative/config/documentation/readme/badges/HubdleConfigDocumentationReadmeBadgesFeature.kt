@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation.readme.badges

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleConfigDocumentationReadmeBadgesFeature::class)
public open class HubdleConfigDocumentationReadmeBadgesFeature :
    Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(
                HubdleConfigDocumentationReadmeBadgesApplyAction.NAME,
                HubdleConfigDocumentationReadmeBadgesApplyAction::class,
            )
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
