package com.javiersc.hubdle.extensions.config.documentation.changelog.tasks

import com.javiersc.hubdle.extensions.config.documentation.changelog.internal.Changelog
import com.javiersc.hubdle.extensions.config.documentation.changelog.internal.changelogFile
import com.javiersc.hubdle.extensions.config.documentation.changelog.internal.fromFile
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

public abstract class FormatChangelogTask : DefaultTask() {

    @InputFile public val changelogFile: File = project.changelogFile

    init {
        group = "changelog"
    }

    @TaskAction
    public fun run() {
        changelogFile.writeText(Changelog.fromFile(changelogFile).toString())
    }

    public companion object {
        public const val name: String = "changelogFormatApply"
    }
}
