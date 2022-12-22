package com.javiersc.hubdle.extensions.kotlin._internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun Project.forKotlinSets(vararg names: String, action: KotlinSourceSet.() -> Unit) {
    val sets = the<KotlinProjectExtension>().sourceSets.filter { it.name in names }
    for (set in sets) action(set)
}

internal fun Project.forKotlinSetsDependencies(
    vararg names: String,
    action: KotlinDependencyHandler.() -> Unit
) {
    forKotlinSets(*names) { dependencies { action(this) } }
}
