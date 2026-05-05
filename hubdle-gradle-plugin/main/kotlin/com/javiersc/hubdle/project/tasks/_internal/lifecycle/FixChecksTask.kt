package com.javiersc.hubdle.project.tasks._internal.lifecycle

import com.javiersc.hubdle.project.tasks.HubdleTask
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault
public open class FixChecksTask : DefaultTask(), HubdleTask {

    init {
        group = VERIFICATION_GROUP
    }

    public companion object {

        public const val NAME: String = "fixChecks"

        public fun register(project: Project) {
            project.tasks.register(NAME)
        }
    }
}
