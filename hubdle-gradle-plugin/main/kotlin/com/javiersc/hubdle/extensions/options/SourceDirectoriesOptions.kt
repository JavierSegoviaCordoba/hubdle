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
    sourceSets.all { configJavaDefaultSourceSets() }
}

internal fun KotlinProjectExtension.configureDefaultKotlinSourceSets() {
    sourceSets.all { configKotlinDefaultSourceSets() }
}

internal fun AndroidSourceSet.configDefaultAndroidSourceSets() {
    configAndroidSourceDirectorySet(assets to "assets")
    configAndroidSourceDirectorySet(java to "kotlin")
    configAndroidSourceDirectory(manifest to "AndroidManifest.xml")
    configAndroidSourceDirectorySet(res to "res")
    configAndroidSourceDirectorySet(resources to "resources")
}

internal fun KotlinSourceSet.configKotlinDefaultSourceSets() {
    kotlin.setSrcDirs(listOf("${this.name}/kotlin"))
    resources.setSrcDirs(listOf("${this.name}/resources"))
}

internal fun SourceSet.configJavaDefaultSourceSets() {
    java.setSrcDirs(listOf("${this.name}/java"))
    resources.setSrcDirs(listOf("${this.name}/resources"))
}

internal fun Named.configAndroidSourceDirectory(sourceSetToName: Pair<AndroidSourceFile, String>) {
    val (sourceSet, name) = sourceSetToName
    sourceSet.srcFile("${this.name}/$name")
}

internal fun Named.configAndroidSourceDirectorySet(
    sourceSetToName: Pair<AndroidSourceDirectorySet, String>
) {
    val (sourceSet, name) = sourceSetToName
    sourceSet.setSrcDirs(listOf("${this.name}/$name"))
}
