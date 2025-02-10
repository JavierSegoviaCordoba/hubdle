package com.javiersc.hubdle.project.extensions.kotlin._internal

import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal val KotlinSourceSet.isKotlinTest: Boolean
    get() = name.startsWith("test") || name.endsWith("Test")
