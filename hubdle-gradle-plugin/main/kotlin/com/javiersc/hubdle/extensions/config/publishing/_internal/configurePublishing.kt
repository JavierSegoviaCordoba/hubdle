package com.javiersc.hubdle.extensions.config.publishing._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

internal fun configureKotlinPublishingRawConfig(project: Project) {
    project.hubdleState.config.publishing.rawConfig.publishing?.execute(project.the())
    project.hubdleState.config.publishing.rawConfig.signing?.execute(project.the())
}
