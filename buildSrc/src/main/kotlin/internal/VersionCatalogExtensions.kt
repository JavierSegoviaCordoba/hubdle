@file:Suppress("PackageDirectoryMismatch")

import org.gradle.accessors.dm.LibrariesForPluginLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

val Project.pluginLibsAccessors: LibrariesForPluginLibs
    get() = the()
