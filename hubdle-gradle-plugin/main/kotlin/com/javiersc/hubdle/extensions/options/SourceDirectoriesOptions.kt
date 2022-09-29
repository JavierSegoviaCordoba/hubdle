package com.javiersc.hubdle.extensions.options

import com.android.build.api.dsl.AndroidSourceDirectorySet
import com.android.build.api.dsl.AndroidSourceFile
import com.android.build.api.dsl.AndroidSourceSet
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public interface SourceDirectoriesOptions<out T> {

    public val Project.sourceSets: NamedDomainObjectContainer<out T>
}

internal fun JavaPluginExtension.configureDefaultJavaSourceSets() {
    sourceSets.all { set -> set.configJavaDefaultSourceSets() }
}

internal fun KotlinProjectExtension.configureDefaultKotlinSourceSets() {
    sourceSets.all { set -> set.configKotlinDefaultSourceSets() }
}

internal fun AndroidSourceSet.configDefaultAndroidSourceSets() {
    configAndroidSourceDirectorySet(assets to "assets")
    configAndroidSourceDirectorySet(java to "kotlin")
    configAndroidSourceDirectory(manifest to "AndroidManifest.xml")
    configAndroidSourceDirectorySet(res to "res")
    configAndroidSourceDirectorySet(resources to "resources")
}

internal fun KotlinSourceSet.configKotlinDefaultSourceSets() {
    val directory = calculateSourceSetDirectory()

    kotlin.setSrcDirs(listOf("$directory/kotlin"))
    resources.setSrcDirs(listOf("$directory/resources"))
}

internal fun SourceSet.configJavaDefaultSourceSets() {
    val directory = name.calculateSourceSetDirectory()
    java.setSrcDirs(listOf("$directory/java"))
    resources.setSrcDirs(listOf("$directory/resources"))
}

internal fun Named.configAndroidSourceDirectory(sourceSetToType: Pair<AndroidSourceFile, String>) {
    val (sourceSet, type) = sourceSetToType
    sourceSet.srcFile("${this.calculateSourceSetDirectory()}/$type")
}

internal fun Named.configAndroidSourceDirectorySet(
    sourceSetToType: Pair<AndroidSourceDirectorySet, String>
) {
    val (sourceSet, type) = sourceSetToType
    sourceSet.setSrcDirs(listOf("${this.calculateSourceSetDirectory()}/$type"))
}

private fun String.calculateSourceSetDirectory(): String {
    val directory =
        when {
            this.endsWith("Main") -> "${this.substringBeforeLast("Main")}/main"
            this.endsWith("Test") -> "${this.substringBeforeLast("Test")}/test"
            else -> this
        }
    return directory
}

private fun Named.calculateSourceSetDirectory(): String = this.name.calculateSourceSetDirectory()
