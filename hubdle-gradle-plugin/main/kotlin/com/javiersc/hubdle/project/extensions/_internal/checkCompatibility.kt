package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.hubdle.project.extensions.kotlin.android.application.hubdleAndroidApplication
import com.javiersc.hubdle.project.extensions.kotlin.android.hubdleAndroid
import com.javiersc.hubdle.project.extensions.kotlin.android.library.hubdleAndroidLibrary
import com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit.hubdleKotlinCompilerLibrary
import com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit.hubdleKotlinCompilerPlugin
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import org.gradle.api.Project

internal fun Project.checkCompatibility() {
    checkOnlyOneKotlinApplied()
}

private fun Project.checkOnlyOneKotlinApplied() {
    val isAndroidEnabled = hubdleAndroid.isEnabled.get()
    val isAndroidApplicationEnabled = hubdleAndroidApplication.isFullEnabled.get()
    val isAndroidLibraryEnabled = hubdleAndroidLibrary.isFullEnabled.get()
    val isJvmEnabled = hubdleKotlinJvm.isFullEnabled.get()
    val isMultiplatformEnabled = hubdleKotlinMultiplatform.isFullEnabled.get()
    val isCompilerLibraryEnabled = hubdleKotlinCompilerLibrary.isFullEnabled.get()
    val isCompilerPluginEnabled = hubdleKotlinCompilerPlugin.isFullEnabled.get()

    val enabled =
        listOf(
            isAndroidApplicationEnabled,
            isAndroidLibraryEnabled,
            isJvmEnabled,
            isMultiplatformEnabled,
            isCompilerLibraryEnabled,
            isCompilerPluginEnabled,
        )

    val hasOnlyOneOrZeroKotlinEnabled = enabled.count { it } <= 1

    check(hasOnlyOneOrZeroKotlinEnabled) {
        """
        | 
        |There is more than one `kotlin` project enabled:
        |  - Android(isEnabled = $isAndroidEnabled)
        |    - Application(isEnabled = $isAndroidApplicationEnabled)
        |    - Library(isEnabled = $isAndroidLibraryEnabled)
        |  - Jvm(isEnabled = $isJvmEnabled)
        |  - Multiplatform(isEnabled = $isMultiplatformEnabled)
        |  - Compiler library(isEnabled = $isCompilerLibraryEnabled)
        |  - Compiler plugin(isEnabled = $isCompilerPluginEnabled)
        |"""
            .trimMargin()
    }
}
