package com.javiersc.hubdle.extensions.config.publishing._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.the
import org.gradle.plugins.signing.SigningExtension

internal fun configureKotlinPublishingRawConfig(project: Project) {
    project.hubdleState.config.publishing.rawConfig.publishing?.execute(project.the())
    val signingExtension: SigningExtension? = project.extensions.findByType()
    if (signingExtension != null) {
        project.hubdleState.config.publishing.rawConfig.signing?.execute(signingExtension)
    }
}
