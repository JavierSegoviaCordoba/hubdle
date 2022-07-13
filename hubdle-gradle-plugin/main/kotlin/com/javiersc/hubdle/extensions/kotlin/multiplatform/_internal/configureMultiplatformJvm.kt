package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun configureMultiplatformJvm(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.jvm.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { jvm() }
    }
}
