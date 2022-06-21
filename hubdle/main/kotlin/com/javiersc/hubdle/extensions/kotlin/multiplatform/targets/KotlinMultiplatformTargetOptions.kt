package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.kotlin.multiplatform.kotlinMultiplatformExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public interface KotlinMultiplatformTargetOptions {

    public val name: String

    public fun Project.main(action: Action<KotlinSourceSet> = Action {}) {
        kotlinMultiplatformExtension.sourceSets.named("${name}Main", action::execute)
    }

    public fun Project.test(action: Action<KotlinSourceSet> = Action {}) {
        kotlinMultiplatformExtension.sourceSets.named("${name}Test", action::execute)
    }
}
