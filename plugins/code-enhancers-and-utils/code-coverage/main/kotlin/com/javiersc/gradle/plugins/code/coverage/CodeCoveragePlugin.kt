package com.javiersc.gradle.plugins.code.coverage

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class CodeCoveragePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("kover")
    }
}
