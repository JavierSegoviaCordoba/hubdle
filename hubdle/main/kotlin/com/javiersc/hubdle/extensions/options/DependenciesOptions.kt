package com.javiersc.hubdle.extensions.options

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

public interface KotlinDependenciesOptions {
    public fun Project.dependencies(configuration: KotlinDependencyHandler.() -> Unit)
}

/**
 * For missing dependencies or features from `DependencyHandler` in `KotlinDependencyHandler`, they
 * can be accessed by using the getter `Property.dependencies: DependencyHandler`
 */
public interface GradleDependenciesOptions {
    public fun Project.gradleApi(): Dependency = dependencies.gradleApi()
    public fun Project.localGroovy(): Dependency = dependencies.localGroovy()
    public fun Project.gradleTestKit(): Dependency = dependencies.gradleTestKit()
}
