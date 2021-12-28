package com.javiersc.gradle.plugins.changelog.tasks

import com.javiersc.gradle.plugins.changelog.internal.Changelog
import com.javiersc.gradle.plugins.changelog.internal.changelogFile
import com.javiersc.gradle.plugins.changelog.internal.fromFile
import com.javiersc.gradle.plugins.changelog.internal.merged
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class MergeChangelogTask : DefaultTask() {

    @OptIn(ExperimentalStdlibApi::class)
    @TaskAction
    fun run() {
        group = "changelog"

        val changelog: Changelog = Changelog.fromFile(project.changelogFile)
        project.changelogFile.writeText(changelog.merged().toString())
    }

    companion object {
        const val name: String = "mergeChangelog"
    }
}
