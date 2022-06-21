package org.gradle.kotlin.dsl

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency

/**
 * For missing dependencies or features from `DependencyHandler` in `KotlinDependencyHandler`, they
 * can be accessed by using the getter `Property.dependencies: DependencyHandler`
 */

/** Gradle API accessor */
public fun Project.gradleApi(): Dependency = dependencies.gradleApi()

/** Local Groovy accessor */
public fun Project.localGroovy(): Dependency = dependencies.localGroovy()

/** Gradle Test Kit accessor */
public fun Project.gradleTestKit(): Dependency = dependencies.gradleTestKit()
