package com.javiersc.hubdle.project.tasks._internal.lifecycle

import com.javiersc.hubdle.project.tasks.HubdleTask
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.BasePlugin.ASSEMBLE_TASK_NAME
import org.gradle.api.plugins.BasePlugin.BUILD_GROUP
import org.gradle.api.tasks.TaskProvider
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault
public open class GenerateTask : DefaultTask(), HubdleTask {

    init {
        group = BUILD_GROUP
    }

    public companion object {

        public const val NAME: String = "generate"

        public fun register(project: Project) {
            val generateTask: TaskProvider<Task> = project.tasks.register(NAME)
            project.tasks
                .named { name -> name == ASSEMBLE_TASK_NAME }
                .configureEach { task -> task.dependsOn(generateTask) }
        }
    }
}
