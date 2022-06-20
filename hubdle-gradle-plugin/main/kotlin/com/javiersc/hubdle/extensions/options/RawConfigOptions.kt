package com.javiersc.hubdle.extensions.options

import com.javiersc.hubdle.extensions.HubdleDslMarker
import org.gradle.api.Action
import org.gradle.api.Project

public interface RawConfigOptions<T> {

    @HubdleDslMarker public fun Project.rawConfig(action: Action<T> = Action {})
}
