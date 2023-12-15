package com.javiersc.hubdle.project.tasks.lifecycle

import com.javiersc.hubdle.project.tasks.HubdleTask
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

public open class FixChecksTask : DefaultTask(), HubdleTask {

    init {
        group = VERIFICATION_GROUP
    }

    public companion object {

        public const val NAME: String = "fixChecks"

        internal fun register(project: Project): TaskProvider<FixChecksTask> =
            project.tasks.register<FixChecksTask>(NAME)
    }
}
