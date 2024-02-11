package com.javiersc.hubdle.project.tasks.lifecycle

import com.javiersc.hubdle.project.tasks.HubdleTask
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin.BUILD_GROUP
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.register

public open class GenerateTask : DefaultTask(), HubdleTask {

    init {
        group = BUILD_GROUP
    }

    public companion object {

        public const val NAME: String = "generate"

        internal fun register(project: Project): TaskProvider<GenerateTask> =
            project.tasks.register<GenerateTask>(NAME)
    }
}
