package com.javiersc.hubdle.project.extensions.kotlin.android._internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.hubdle.project.HubdleProperty.Android
import com.javiersc.hubdle.project.extensions.shared.features.hubdleJavaVersionFeature
import com.javiersc.kotlin.stdlib.remove
import java.io.File
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

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

internal fun Project.calculateAndroidNamespace(initial: String?): String? =
    when {
        initial != null -> initial
        getBooleanProperty(Android.namespaceUseProject).orNull == true -> {
            calculateAndroidNamespaceWithProject()
        }
        getBooleanProperty(Android.namespaceUseKotlinFile).orNull == true -> {
            calculateAndroidNamespaceWithKotlinFile()
        }
        else -> calculateAndroidNamespaceWithProject()
    }

private fun Project.calculateAndroidNamespaceWithProject(): String {
    val sanitizedProjectGroup = group.toString().sanitize()
    val sanitizedProjectName = name.sanitize()
    val splitByDot = "$sanitizedProjectGroup.$sanitizedProjectName".split(".")
    return splitByDot.reduce { acc, current ->
        if (acc.split(".").lastOrNull() == current) acc else "$acc.$current"
    }
}

private fun Project.calculateAndroidNamespaceWithKotlinFile(): String? =
    extensions
        .findByType<KotlinProjectExtension>()
        ?.sourceSets
        ?.asMap
        ?.values
        ?.asSequence()
        ?.flatMap { it.kotlin.srcDirs }
        ?.firstOrNull { it.path.endsWith("main${File.separator}kotlin") }
        ?.walkTopDown()
        ?.onEnter { parentFile ->
            parentFile.listFiles().orEmpty().firstOrNull { it.extension == "kt" } == null
        }
        ?.flatMap { it.listFiles().orEmpty().toList() }
        ?.firstOrNull { it.listFiles().orEmpty().any { it.extension == "kt" } }
        ?.listFiles()
        ?.firstOrNull { it.extension == "kt" }
        ?.readLines()
        ?.firstOrNull { it.startsWith("package ") }
        ?.remove("package ")

private fun String.sanitize(): String =
    this.replace(File.separator, ".").replace("-", ".").replace("_", ".")
