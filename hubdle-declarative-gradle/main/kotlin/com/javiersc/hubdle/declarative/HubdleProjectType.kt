@file:Suppress("UnstableApiUsage")

package com.javiersc.hubdle.declarative

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectType
import org.gradle.features.binding.ProjectTypeBinding
import org.gradle.features.binding.ProjectTypeBindingBuilder
import org.gradle.features.dsl.bindProjectType

@BindsProjectType(HubdleProjectType::class)
internal open class HubdleProjectType : Plugin<Project>, ProjectTypeBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectTypeBindingBuilder) {
        builder
            .bindProjectType(HubdleApplyAction.NAME, HubdleApplyAction::class)
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
