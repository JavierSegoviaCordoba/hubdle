package com.javiersc.sandbox

import com.javiersc.gradle.logging.extensions.infoColored
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class SandboxPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.logger.infoColored { "Sandbox plugin for testing purposes" }
    }
}
