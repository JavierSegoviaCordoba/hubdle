@file:Suppress("UnstableApiUsage")

package hubdle.declarative.versioning.semver

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleVersioningSemverFeature::class)
public open class HubdleVersioningSemverFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(
                HubdleVersioningSemverApplyAction.NAME,
                HubdleVersioningSemverApplyAction::class,
            )
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
