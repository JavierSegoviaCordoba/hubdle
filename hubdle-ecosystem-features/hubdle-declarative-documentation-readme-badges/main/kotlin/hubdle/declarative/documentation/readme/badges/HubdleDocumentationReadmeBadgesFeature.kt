@file:Suppress("UnstableApiUsage")

package hubdle.declarative.documentation.readme.badges

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleDocumentationReadmeBadgesFeature::class)
public open class HubdleDocumentationReadmeBadgesFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(
                HubdleDocumentationReadmeBadgesApplyAction.NAME,
                HubdleDocumentationReadmeBadgesApplyAction::class,
            )
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
