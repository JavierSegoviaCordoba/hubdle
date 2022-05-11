package com.javiersc.gradle.plugins.changelog.tasks

import com.javiersc.gradle.plugins.changelog.internal.Changelog
import com.javiersc.gradle.plugins.changelog.internal.changelogFile
import com.javiersc.gradle.plugins.changelog.internal.fromFile
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

abstract class FormatChangelogTask : DefaultTask() {

    @get:InputFile
    val changelogFile: File
        get() = project.changelogFile

    @TaskAction
    fun run() {
        group = "changelog"

        changelogFile.writeText(Changelog.fromFile(changelogFile).toString())
    }

    companion object {
        const val name: String = "formatChangelog"
    }
}
