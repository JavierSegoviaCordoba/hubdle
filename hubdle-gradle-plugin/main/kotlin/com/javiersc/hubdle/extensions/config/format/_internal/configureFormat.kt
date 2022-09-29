package com.javiersc.hubdle.extensions.config.format._internal

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import java.io.File
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun configureFormat(project: Project) {
    val format = project.hubdleState.config.format

    if (format.isEnabled) {
        project.pluginManager.apply(PluginIds.Format.spotless)

        val checkTask = project.tasks.namedLazily<Task>("check")

        val checkFormat = project.tasks.maybeRegisterLazily<Task>("checkFormat")
        checkFormat.configureEach { task ->
            task.group = "verification"
            task.dependsOn("spotlessCheck")
        }

        checkTask.configureEach { task -> task.dependsOn(checkFormat) }

        val applyFormat = project.tasks.maybeRegisterLazily<Task>("applyFormat")
        applyFormat.configureEach { task ->
            task.group = "verification"
            task.dependsOn("spotlessApply")
        }

        format.excludes += project.excludedSpecialDirs

        if (project.isRootProject) {
            project.configure<SpotlessExtension> { predeclareDepsFromBuildscript() }

            project.configure<SpotlessExtensionPredeclare> {
                kotlin { kotlin -> kotlin.ktfmt(format.ktfmtVersion) }
            }
        }

        if (project.hubdleState.kotlin.isEnabled) {
            val checkKotlinFormat = project.tasks.maybeRegisterLazily<Task>("checkKotlinFormat")
            checkKotlinFormat.configureEach { task ->
                task.group = "verification"
                task.dependsOn("spotlessKotlinCheck")
            }

            checkTask.configureEach { task -> task.dependsOn(checkKotlinFormat) }

            project.tasks.maybeRegisterLazily<Task>("applyKotlinFormat").configureEach { task ->
                task.group = "verification"
                task.dependsOn("spotlessKotlinApply")
            }

            format.includes += project.includedKotlinSourceSetDirsKotlinFiles
            format.excludes += project.excludedResourceSourceSetDirsKotlinFiles

            project.configure<SpotlessExtension> {
                kotlin { kotlin ->
                    kotlin.target(format.includes.distinct())
                    kotlin.targetExclude(format.excludes.distinct())
                    kotlin.ktfmt(format.ktfmtVersion).kotlinlangStyle()
                }
            }
        }
    }
}

private val Project.includedKotlinSourceSetDirsKotlinFiles: Set<String>
    get() =
        the<KotlinProjectExtension>()
            .sourceSets
            .asSequence()
            .flatMap { it.kotlin.srcDirs }
            .map { it.relativeTo(projectDir) }
            .filterNot { it.path.startsWith(buildDir.name) }
            .map { "${it.unixPath}/**/*.kt" }
            .toSet()

private val Project.excludedSpecialDirs: Set<String>
    get() =
        setOf(
            "${buildDir.name}/**/*.kt",
            ".gradle/**/*.kt",
        )

private val Project.excludedResourceSourceSetDirsKotlinFiles: Set<String>
    get() =
        the<KotlinProjectExtension>()
            .sourceSets
            .asSequence()
            .flatMap { it.resources.srcDirs }
            .map { it.relativeTo(projectDir) }
            .map { "${it.unixPath}/**/*.kt" }
            .toSet()

internal fun configureKotlinFormatRawConfig(project: Project) {
    project.hubdleState.config.format.rawConfig.spotless?.execute(project.the())
    project.hubdleState.config.format.rawConfig.spotlessPredeclare?.execute(project.the())
}

private val File.unixPath: String
    get() = path.replace("\\", "/")
