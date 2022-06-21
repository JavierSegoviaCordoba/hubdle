package com.javiersc.hubdle.extensions

import org.gradle.api.Action
import org.gradle.api.Project

public interface RawConfigOptions<T> {
    public fun Project.rawConfig(action: Action<T> = Action {})
}
