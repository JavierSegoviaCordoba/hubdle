package com.javiersc.hubdle.extensions

import org.gradle.api.Action
import org.gradle.api.Project

public interface OriginalConfigOptions<T> {
    public fun Project.originalConfig(action: Action<T> = Action {})
}
