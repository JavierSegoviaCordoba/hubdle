package com.javiersc.hubdle.project.extensions._internal

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider

internal fun Project.createPrepareKotlinIdeaImport(): Task =
    tasks.maybeCreate("prepareKotlinIdeaImport")

internal val Project.prepareKotlinIdeaImport: TaskProvider<Task>
    get() = tasks.named("prepareKotlinIdeaImport")
