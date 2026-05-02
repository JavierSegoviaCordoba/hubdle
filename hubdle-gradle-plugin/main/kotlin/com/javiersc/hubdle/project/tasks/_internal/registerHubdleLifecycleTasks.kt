package com.javiersc.hubdle.project.tasks._internal

import com.javiersc.hubdle.project.extensions._internal.createPrepareKotlinIdeaImport
import hubdle.platform.tasks.lifecycle.FixChecksTask
import hubdle.platform.tasks.lifecycle.GenerateTask
import hubdle.platform.tasks.lifecycle.TestsTask
import org.gradle.api.Project

internal fun Project.registerHubdleLifecycleTasks() {
    FixChecksTask.register(this)
    GenerateTask.register(this)
    TestsTask.register(this)
    createPrepareKotlinIdeaImport()
}
