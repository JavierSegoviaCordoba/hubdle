package com.javiersc.gradle.plugins.versioning

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class VersioningPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("com.javiersc.semver.gradle.plugin")
    }
}
