package com.javiersc.hubdle.extensions.kotlin

import org.gradle.api.Action
import org.gradle.api.Project

public interface MainAndTestKotlinSourceSetsOptions<T> {

    public fun Project.main(action: Action<T> = Action {})

    public fun Project.test(action: Action<T> = Action {})
}
