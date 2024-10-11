package com.javiersc.hubdle.project.tasks.lifecycle

import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.javiersc.hubdle.project.tasks.HubdleTask
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault
public open class TestsTask : DefaultTask(), HubdleTask {

    init {
        group = VERIFICATION_GROUP
    }

    public companion object {

        public const val NAME: String = "tests"

        internal fun register(project: Project) {
            val testsTask: TaskProvider<Task> = project.tasks.register(NAME)
            project.tasks.named(CHECK_TASK_NAME).dependsOn(testsTask)
            testsTask.configure { task -> task.dependsOn(project.tasks.withType<Test>()) }
        }
    }
}
