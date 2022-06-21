package com.javiersc.hubdle.extensions.config.documentation.changelog.tasks

import com.javiersc.hubdle.extensions.config.documentation.changelog.internal.Changelog
import com.javiersc.hubdle.extensions.config.documentation.changelog.internal.changelogFile
import com.javiersc.hubdle.extensions.config.documentation.changelog.internal.fromFile
import com.javiersc.hubdle.extensions.config.documentation.changelog.internal.merged
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

public abstract class MergeChangelogTask : DefaultTask() {

    @InputFile public val changelogFile: File = project.changelogFile

    init {
        group = "changelog"
    }

    @TaskAction
    public fun run() {
        val changelog: Changelog = Changelog.fromFile(changelogFile)
        changelogFile.writeText(changelog.merged().toString())
    }

    public companion object {
        public const val name: String = "mergeChangelog"
    }
}
