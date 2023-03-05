package com.javiersc.hubdle.project.extensions.config.documentation.changelog

import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.Changelog
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.changelogFile
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.fromFile
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

public abstract class ApplyFormatChangelogTask : DefaultTask() {

    @InputFile public val changelogFile: File = project.changelogFile

    init {
        group = "changelog"
    }

    @TaskAction
    public fun run() {
        changelogFile.writeText(Changelog.fromFile(changelogFile).toString())
    }

    public companion object {
        public const val NAME: String = "applyFormatChangelog"
    }
}
