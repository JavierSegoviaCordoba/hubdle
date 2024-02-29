package com.javiersc.hubdle.project.extensions.android._internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

internal typealias AndroidCommonExtension = CommonExtension<*, *, *, *, *, *>

internal fun Project.findAndroidCommonExtension(): AndroidCommonExtension? =
    extensions.findByType<LibraryExtension>() ?: extensions.findByType<ApplicationExtension>()

internal fun Project.androidCommonExtension(): AndroidCommonExtension =
    findAndroidCommonExtension() ?: error("Android library or application plugin not being applied")

internal fun Project.configureAndroidCommonExtension(block: AndroidCommonExtension.() -> Unit) {
    block(androidCommonExtension())
}
