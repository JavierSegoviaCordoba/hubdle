package com.javiersc.hubdle.extensions.options

import com.android.build.api.dsl.AndroidSourceDirectorySet
import com.android.build.api.dsl.AndroidSourceFile
import com.android.build.api.dsl.AndroidSourceSet
import com.javiersc.kotlin.stdlib.decapitalize
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

internal fun KotlinProjectExtension.configureDefaultKotlinSourceSets(
    targets: Set<String> = emptySet()
) {
    sourceSets.all { set -> set.configKotlinDefaultSourceSets(targets) }
}

internal fun AndroidSourceSet.configDefaultAndroidSourceSets() {
    configAndroidSourceDirectorySet(assets to "assets")
    configAndroidSourceDirectorySet(java to "kotlin")
    configAndroidSourceDirectory(manifest to "AndroidManifest.xml")
    configAndroidSourceDirectorySet(res to "res")
    configAndroidSourceDirectorySet(resources to "resources")
}

internal fun KotlinSourceSet.configKotlinDefaultSourceSets(targets: Set<String>) {
    val directory = calculateKmpSourceSetDirectory(targets)

    kotlin.setSrcDirs(listOf("$directory/kotlin"))
    resources.setSrcDirs(listOf("$directory/resources"))
}

internal fun SourceSet.configJavaDefaultSourceSets() {
    java.setSrcDirs(listOf("$name/java"))
    resources.setSrcDirs(listOf("$name/resources"))
}

internal fun Named.configAndroidSourceDirectory(sourceSetToType: Pair<AndroidSourceFile, String>) {
    val (sourceSet, type) = sourceSetToType
    sourceSet.srcFile("$name/$type")
}

internal fun Named.configAndroidSourceDirectorySet(
    sourceSetToType: Pair<AndroidSourceDirectorySet, String>
) {
    val (sourceSet, type) = sourceSetToType
    sourceSet.setSrcDirs(listOf("$name/$type"))
}

private fun Named.calculateKmpSourceSetDirectory(targets: Set<String>): String {

    val target =
        targets
            .filter { target -> name.startsWith(target) }
            .maxByOrNull { target -> target.count() }

    val directory =
        when {
            target != null -> {
                val type = name.substringAfter(target).decapitalize()
                "$target/$type"
            }
            else -> name
        }
    return directory
}
