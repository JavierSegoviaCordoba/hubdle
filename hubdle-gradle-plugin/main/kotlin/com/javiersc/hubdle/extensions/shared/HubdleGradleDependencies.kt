package com.javiersc.hubdle.extensions.shared

import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.gradleKotlinDsl as originalGradleKotlinDsl

/**
 * For missing dependencies or features from `DependencyHandler` in `KotlinDependencyHandler`, they
 * can be accessed by using the getter:
 * ```kotlin
 * val Property.dependencies: DependencyHandler
 * ```
 */
public interface HubdleGradleDependencies {

    public fun Project.gradleApi(): Dependency = dependencies.gradleApi()

    public fun Project.gradleKotlinDsl(): Dependency = originalGradleKotlinDsl()

    public fun Project.localGroovy(): Dependency = dependencies.localGroovy()

    public fun Project.gradleTestKit(): Dependency = dependencies.gradleTestKit()

    public fun HubdleEnableableExtension.gradleApi(): Dependency = project.dependencies.gradleApi()

    public fun HubdleEnableableExtension.gradleKotlinDsl(): Dependency =
        project.originalGradleKotlinDsl()

    public fun HubdleEnableableExtension.localGroovy(): Dependency =
        project.dependencies.localGroovy()

    public fun HubdleEnableableExtension.gradleTestKit(): Dependency =
        project.dependencies.gradleTestKit()
}
