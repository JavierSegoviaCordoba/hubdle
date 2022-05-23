package com.javiersc.gradle.plugins.changelog.tasks

import com.javiersc.gradle.plugins.changelog.internal.Changelog
import com.javiersc.gradle.plugins.changelog.internal.changelogFile
import com.javiersc.gradle.plugins.changelog.internal.fromFile
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

abstract class FormatChangelogTask : DefaultTask() {

    @InputFile val changelogFile: File = project.changelogFile

    init {
        group = "changelog"
    }

    @TaskAction
    fun run() {
        changelogFile.writeText(Changelog.fromFile(changelogFile).toString())
    }

    companion object {
        const val name: String = "formatChangelog"
    }
}
