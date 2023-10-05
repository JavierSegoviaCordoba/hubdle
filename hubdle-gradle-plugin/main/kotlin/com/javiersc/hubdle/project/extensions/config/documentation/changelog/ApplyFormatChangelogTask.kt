package com.javiersc.hubdle.project.extensions.config.documentation.changelog

import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.Changelog
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.changelogFile
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.fromFile
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
public abstract class ApplyFormatChangelogTask : DefaultTask() {

    @InputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    public val inputFile: File = project.changelogFile

    @OutputFile public val outputFile: File = project.changelogFile

    init {
        group = "changelog"
    }

    @TaskAction
    public fun run() {
        val changelogPatchedContent = "${Changelog.fromFile(inputFile)}"
        outputFile.writeText(changelogPatchedContent)
    }

    public companion object {
        public const val NAME: String = "applyFormatChangelog"
    }
}
