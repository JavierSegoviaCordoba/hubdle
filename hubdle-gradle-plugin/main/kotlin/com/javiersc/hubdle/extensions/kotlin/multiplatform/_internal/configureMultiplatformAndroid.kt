package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.android._internal.calculateAndroidNamespace
import com.javiersc.hubdle.extensions.kotlin.android._internal.configureAndroidBuildFeatures
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun configureMultiplatformAndroid(project: Project) {
    val multiplatformAndroidState = project.hubdleState.kotlin.multiplatform.android
    val androidState = project.hubdleState.kotlin.android

    if (multiplatformAndroidState.isEnabled) {
        project.pluginManager.apply(PluginIds.Android.library)
        project.configure<LibraryExtension> {
            compileSdk = androidState.compileSdk
            defaultConfig.minSdk = androidState.minSdk
            namespace = project.calculateAndroidNamespace(androidState.namespace)

            sourceSets.all { set ->
                set.manifest.srcFile("android/${set.name}/AndroidManifest.xml")
            }
            project.configureAndroidBuildFeatures(this)
        }

        project.configure<KotlinMultiplatformExtension> {
            android {
                if (project.hubdleState.config.publishing.isEnabled) {
                    when {
                        androidState.library.publishLibraryVariants.isNotEmpty() -> {
                            val variants =
                                androidState.library.publishLibraryVariants.toTypedArray()
                            publishLibraryVariants(*variants)
                        }
                        androidState.library.allLibraryVariants -> publishAllLibraryVariants()
                    }
                }
            }
        }
    }
}
