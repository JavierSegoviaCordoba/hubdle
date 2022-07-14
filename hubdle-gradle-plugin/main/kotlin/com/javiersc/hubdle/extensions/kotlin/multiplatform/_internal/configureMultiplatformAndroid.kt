package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin._internal.calculateAndroidNamespace
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun configureMultiplatformAndroid(project: Project) {
    val androidState = project.hubdleState.kotlin.multiplatform.android

    if (androidState.isEnabled) {
        project.pluginManager.apply(PluginIds.Android.library)
        project.configure<LibraryExtension> {
            compileSdk = androidState.compileSdk
            defaultConfig.minSdk = androidState.minSdk
            namespace = project.calculateAndroidNamespace(androidState.namespace)

            sourceSets.all { manifest.srcFile("android/$name/AndroidManifest.xml") }
        }

        project.configure<KotlinMultiplatformExtension> {
            android {
                if (project.hubdleState.config.publishing.isEnabled) {
                    when {
                        androidState.publishLibraryVariants.isNotEmpty() -> {
                            val variants = androidState.publishLibraryVariants.toTypedArray()
                            publishLibraryVariants(*variants)
                        }
                        androidState.allLibraryVariants -> publishAllLibraryVariants()
                    }
                }
            }
        }
    }
}
