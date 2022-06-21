package com.javiersc.hubdle.extensions._internal.state.kotlin

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import com.javiersc.hubdle.extensions.kotlin.jvm.javaExtension
import com.javiersc.hubdle.extensions.kotlin.kotlinExtension
import org.gradle.api.Project

internal fun configureMultiplatform(project: Project) {
    if (hubdleState.kotlin.multiplatform.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)

        project.configureExplicitApi()
        project.configJvmTarget()
        project.javaExtension.configureDefaultJavaSourceSets()
        project.kotlinExtension.configureDefaultKotlinSourceSets()

        if (hubdleState.kotlin.isPublishingEnabled) {
            project.configurePublishingExtension()
            project.configureSigningForPublishing()
        }
    }
}
