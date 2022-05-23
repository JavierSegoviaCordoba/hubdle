package com.javiersc.gradle.plugins.changelog.tasks

import com.javiersc.gradle.plugins.changelog.internal.Changelog
import com.javiersc.gradle.plugins.changelog.internal.changelogFile
import com.javiersc.gradle.plugins.changelog.internal.fromFile
import com.javiersc.gradle.plugins.changelog.internal.merged
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

abstract class MergeChangelogTask : DefaultTask() {

    @InputFile val changelogFile: File = project.changelogFile

    init {
        group = "changelog"
    }

    @TaskAction
    fun run() {
        val changelog: Changelog = Changelog.fromFile(changelogFile)
        changelogFile.writeText(changelog.merged().toString())
    }

    companion object {
        const val name: String = "mergeChangelog"
    }
}
