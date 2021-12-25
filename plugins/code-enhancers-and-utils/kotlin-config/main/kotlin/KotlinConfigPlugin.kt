package com.javiersc.gradle.plugins.kotlin.config

import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinConfigPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        KotlinConfigType.build(project)
    }
}
