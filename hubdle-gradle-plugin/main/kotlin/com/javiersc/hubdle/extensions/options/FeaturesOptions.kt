package com.javiersc.hubdle.extensions.options

import org.gradle.api.Action

public interface FeaturesOptions<T> {

    public val features: T

    public fun features(action: Action<T> = Action {}): Unit = action.execute(features)
}
