@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.kotlin.library

import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinLibraryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        KotlinLibraryType.build(project)
    }
}
