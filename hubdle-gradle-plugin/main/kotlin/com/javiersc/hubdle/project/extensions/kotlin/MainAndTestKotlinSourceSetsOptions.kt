package com.javiersc.hubdle.project.extensions.kotlin

import org.gradle.api.Action

public interface MainAndTestKotlinSourceSetsOptions<T : Any> {

    public fun main(action: Action<T> = Action {})

    public fun test(action: Action<T> = Action {})
}
