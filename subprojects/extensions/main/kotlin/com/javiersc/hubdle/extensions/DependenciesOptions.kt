package com.javiersc.hubdle.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

public interface DependenciesOptions {
    public fun Project.dependencies(configuration: DependencyHandlerScope.() -> Unit)
}
