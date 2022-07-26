package com.javiersc.hubdle.settings.extensions

import com.javiersc.hubdle.settings.HubdleSettingsDslMarker
import java.io.File
import org.gradle.api.initialization.Settings

@HubdleSettingsDslMarker
public open class HubdleAutoIncludeExtension {

    public var isEnabled: Boolean = true

    internal val includableProjects: Set<String>
        get() = (includes - excludes).filter(String::isNotBlank).toSet()

    internal val includableBuilds: Set<String>
        get() = (includedBuilds - excludedBuilds).filter(String::isNotBlank).toSet()

    private val includes: MutableSet<String> = mutableSetOf()
    private val excludes: MutableSet<String> = mutableSetOf()

    private val includedBuilds: MutableSet<String> = mutableSetOf()
    private val excludedBuilds: MutableSet<String> = mutableSetOf("buildSrc")

    @HubdleSettingsDslMarker
    public fun includes(vararg paths: String) {
        includes += paths.map(String::toProjectPath)
    }

    @HubdleSettingsDslMarker
    public fun excludes(vararg paths: String) {
        excludes += paths.map(String::toProjectPath)
    }

    @HubdleSettingsDslMarker
    public fun includedBuilds(vararg paths: String) {
        includedBuilds += paths.map(String::toIncludedBuildPath)
    }

    @HubdleSettingsDslMarker
    public fun excludedBuilds(vararg paths: String) {
        excludedBuilds += paths.map(String::toIncludedBuildPath)
    }
}

internal fun Settings.extractedProjects(): Set<String> =
    settingsDir
        .walkTopDown()
        .onEnter { file -> file.shouldContinueVisiting(settings) }
        .flatMap { file ->
            val children = file.listFiles()?.filter { child -> child.isProject }
            children?.map { child -> child.relativeTo(settingsDir).path }.orEmpty()
        }
        .toSet()

internal fun Settings.extractedBuildProjects(): Set<String> =
    settingsDir
        .walkTopDown()
        .onEnter { file -> file.shouldContinueVisiting(settings) }
        .flatMap { file ->
            val children = file.listFiles()?.filter { child -> child.isSettingsProject }
            children?.map { child -> child.relativeTo(settingsDir).path }.orEmpty()
        }
        .toSet()

private fun File.shouldContinueVisiting(settings: Settings): Boolean =
    if (isRootDir(settings)) true else !isProject && !isSettingsProject && !isSpecialDirectory

private val File.isSpecialDirectory: Boolean
    get() = name.startsWith(".") || name.startsWith("build") || name == "gradle"

private fun File.isRootDir(settings: Settings): Boolean =
    absoluteFile == settings.settingsDir.absoluteFile

private val File.isProject: Boolean
    get() = hasBuildGradle && !isSettingsProject

private val File.isSettingsProject: Boolean
    get() = hasSettingsGradle

private val File.hasBuildGradle: Boolean
    get() = listFiles()?.any { it.name == "build.gradle.kts" || it.name == "build.gradle" } ?: false

private val File.hasSettingsGradle: Boolean
    get() =
        listFiles()?.any { it.name == "settings.gradle.kts" || it.name == "settings.gradle" }
            ?: false

private fun String.toProjectPath(): String {
    val project = replace(File.separator, ":").replace("/", ":").replace("\\", ":")
    return if (project.startsWith(":")) project else ":$project"
}

private fun String.toIncludedBuildPath(): String =
    replace(File.separator, "/").replace(":", "/").replace("\\", ":").dropWhile {
        it == File.separatorChar || it == '/' || it == '\\'
    }
