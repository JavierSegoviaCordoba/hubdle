package com.javiersc.hubdle.extensions._internal

import com.javiersc.hubdle.extensions.kotlin.android.application.hubdleAndroidApplication
import com.javiersc.hubdle.extensions.kotlin.android.hubdleAndroid
import com.javiersc.hubdle.extensions.kotlin.android.library.hubdleAndroidLibrary
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.hubdleGradlePlugin
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog.hubdleGradleVersionCatalog
import com.javiersc.hubdle.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import org.gradle.api.Project

internal fun Project.checkCompatibility() {
    checkOnlyOneKotlinApplied()
}

private fun Project.checkOnlyOneKotlinApplied() {
    val isAndroidEnabled = hubdleAndroid.isEnabled.get()
    val isAndroidApplicationEnabled = hubdleAndroidApplication.isFullEnabled.get()
    val isAndroidLibraryEnabled = hubdleAndroidLibrary.isFullEnabled.get()
    val isGradlePluginEnabled = hubdleGradlePlugin.isFullEnabled.get()
    val isGradleVersionCatalogEnabled = hubdleGradleVersionCatalog.isFullEnabled.get()
    val isJvmEnabled = hubdleKotlinJvm.isFullEnabled.get()
    val isMultiplatformEnabled = hubdleKotlinMultiplatform.isFullEnabled.get()

    val enabled =
        listOf(
            isAndroidApplicationEnabled,
            isAndroidLibraryEnabled,
            isGradlePluginEnabled,
            isGradleVersionCatalogEnabled,
            isJvmEnabled,
            isMultiplatformEnabled,
        )

    val hasOnlyOneOrZeroKotlinEnabled = enabled.count { it } <= 1

    check(hasOnlyOneOrZeroKotlinEnabled) {
        """ 
            |There is more than one `kotlin` project enabled:
            |  - Android(isEnabled = $isAndroidEnabled)
            |    - Application(isEnabled = $isAndroidApplicationEnabled)
            |    - Library(isEnabled = $isAndroidLibraryEnabled)
            |  - Gradle Plugin(isEnabled = $isGradlePluginEnabled)
            |  - Version Catalog(isEnabled = $isGradleVersionCatalogEnabled)
            |  - Jvm(isEnabled = $isJvmEnabled)
            |  - Multiplatform(isEnabled = $isMultiplatformEnabled)
            |
        """
            .trimMargin()
    }
}
