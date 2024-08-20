package com.javiersc.hubdle.project.extensions.kotlin._internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal fun Project.forKotlinSetsDependencies(
    vararg names: String,
    action: KotlinDependencyHandler.() -> Unit,
) {
    configure<KotlinProjectExtension> {
        sourceSets.configureEach { set -> if (set.name in names) set.dependencies(action) }
    }
}
