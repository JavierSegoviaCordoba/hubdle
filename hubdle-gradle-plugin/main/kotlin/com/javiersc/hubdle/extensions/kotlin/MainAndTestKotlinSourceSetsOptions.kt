package com.javiersc.hubdle.extensions.kotlin

import org.gradle.api.Action

public interface MainAndTestKotlinSourceSetsOptions<T> {

    public fun main(action: Action<T> = Action {})

    public fun test(action: Action<T> = Action {})
}
