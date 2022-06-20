package com.javiersc.hubdle.extensions.options

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency

/**
 * For missing dependencies or features from `DependencyHandler` in `KotlinDependencyHandler`, they
 * can be accessed by using the getter:
 *
 * ```kotlin
 * val Property.dependencies: DependencyHandler
 * ```
 */
public interface GradleDependenciesOptions {
    public fun Project.gradleApi(): Dependency = dependencies.gradleApi()
    public fun Project.localGroovy(): Dependency = dependencies.localGroovy()
    public fun Project.gradleTestKit(): Dependency = dependencies.gradleTestKit()
}
