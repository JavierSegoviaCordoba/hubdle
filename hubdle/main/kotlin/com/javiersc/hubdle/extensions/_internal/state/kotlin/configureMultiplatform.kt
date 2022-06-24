package com.javiersc.hubdle.extensions._internal.state.kotlin

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureBinaryCompatibilityValidator
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureExplicitApi
import com.javiersc.hubdle.extensions.options.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun configureMultiplatform(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)

        project.configureBinaryCompatibilityValidator()
        project.configureExplicitApi()
        project.configJvmTarget()
        project.the<JavaPluginExtension>().configureDefaultJavaSourceSets()
        project.the<KotlinProjectExtension>().configureDefaultKotlinSourceSets()

        if (project.hubdleState.kotlin.isPublishingEnabled) {
            project.configurePublishingExtension()
            project.configureSigningForPublishing()
        }
    }
}

internal fun configureMultiplatformAndroid(project: Project) {
    val androidState = project.hubdleState.kotlin.multiplatform.android

    if (androidState.isEnabled) {
        project.pluginManager.apply(PluginIds.Android.library)
        project.configure<LibraryExtension> {
            sourceSets.all {
                it.manifest.srcFile("android${it.name.capitalized()}/AndroidManifest.xml")
            }
        }

        project.configure<KotlinMultiplatformExtension> {
            android {
                if (project.hubdleState.kotlin.isPublishingEnabled) {
                    when {
                        androidState.allLibraryVariants -> publishAllLibraryVariants()
                        androidState.publishLibraryVariants.isNotEmpty() -> {
                            val variants = androidState.publishLibraryVariants.toTypedArray()
                            publishLibraryVariants(*variants)
                        }
                    }
                }
            }
        }
    }
}

internal fun configureMultiplatformJvm(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.jvm.isEnabled) {
        project.configure<KotlinMultiplatformExtension> { jvm() }
    }
}
