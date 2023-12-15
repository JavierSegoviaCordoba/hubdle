package com.javiersc.hubdle.project.tasks._internal

import com.javiersc.hubdle.project.tasks.lifecycle.FixChecksTask
import com.javiersc.hubdle.project.tasks.lifecycle.TestsTask
import org.gradle.api.Project

internal fun Project.registerHubdleLifecycleTasks() {
    FixChecksTask.register(this)
    TestsTask.register(this)
}
