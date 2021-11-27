package com.javiersc.gradle.plugins.changelog.tasks

import com.javiersc.gradle.plugins.changelog.internal.Changelog
import com.javiersc.gradle.plugins.changelog.internal.changelogFile
import com.javiersc.gradle.plugins.changelog.internal.fromFile
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class FormatChangelogTask : DefaultTask() {

    @TaskAction
    fun run() {
        group = "changelog"

        project.changelogFile.writeText(Changelog.fromFile(project.changelogFile).toString())
    }

    companion object {
        const val name: String = "formatChangelog"
    }
}
