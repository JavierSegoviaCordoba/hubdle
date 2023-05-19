package com.javiersc.hubdle.project.extensions.kotlin.android._internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.project.extensions.shared.features.hubdleJavaVersionFeature
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureAndroidApplicationJavaVersion() {
    configure<ApplicationExtension> {
        compileOptions {
            sourceCompatibility = hubdleJavaVersionFeature.jvmVersion.get()
            targetCompatibility = hubdleJavaVersionFeature.jvmVersion.get()
        }
    }
}

internal fun Project.configureAndroidLibraryJavaVersion() {
    configure<LibraryExtension> {
        compileOptions {
            sourceCompatibility = hubdleJavaVersionFeature.jvmVersion.get()
            targetCompatibility = hubdleJavaVersionFeature.jvmVersion.get()
        }
    }
}
