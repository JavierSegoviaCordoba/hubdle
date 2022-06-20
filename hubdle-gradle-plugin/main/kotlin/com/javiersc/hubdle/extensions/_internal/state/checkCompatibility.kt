package com.javiersc.hubdle.extensions._internal.state

import org.gradle.api.Project

public fun Project.checkCompatibility() {
    checkOnlyOneKotlinApplied()
}

private fun Project.checkOnlyOneKotlinApplied() {
    val state = hubdleState.kotlin

    val isAndroidLibraryEnabled = state.android.library
    val isGradlePluginEnabled = state.gradle.plugin
    val isGradleVersionCatalogEnabled = state.gradle.versionCatalog
    val isJvmEnabled = state.jvm
    val isMultiplatformEnabled = state.multiplatform

    val enabled =
        listOf(
            isAndroidLibraryEnabled,
            isGradlePluginEnabled,
            isGradleVersionCatalogEnabled,
            isJvmEnabled,
            isMultiplatformEnabled,
        )

    check(enabled.count(Enableable::isEnabled) <= 1) {
        """ 
            |There is more than one `kotlin` project enabled:
            |  - Android library(isEnabled = ${state.android.library.isEnabled})
            |  - Gradle Plugin(isEnabled = ${state.gradle.plugin.isEnabled})
            |  - Version Catalog(isEnabled = ${state.gradle.versionCatalog.isEnabled})
            |  - Jvm(isEnabled = ${state.jvm.isEnabled})
            |  - Multiplatform(isEnabled = ${state.multiplatform.isEnabled})
            |
        """.trimMargin()
    }
}
