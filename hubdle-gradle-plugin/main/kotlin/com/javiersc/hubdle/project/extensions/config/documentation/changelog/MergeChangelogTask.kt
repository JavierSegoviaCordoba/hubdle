package com.javiersc.hubdle.project.extensions.config.documentation.changelog

import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.Changelog
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.fromFile
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.merged
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
public abstract class MergeChangelogTask
@Inject
constructor(objects: ObjectFactory, layout: ProjectLayout) : DefaultTask() {

    @InputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    public val changelogFileInput: RegularFileProperty =
        objects.fileProperty().convention(layout.projectDirectory.file("CHANGELOG.md"))

    @OutputFile
    public val changelogFileOutput: RegularFileProperty =
        objects.fileProperty().convention(layout.projectDirectory.file("CHANGELOG.md"))

    init {
        group = "changelog"
    }

    @TaskAction
    public fun run() {
        val changelog: Changelog = Changelog.fromFile(changelogFileInput.get().asFile)
        changelogFileOutput.get().asFile.writeText(changelog.merged().toString())
    }

    public companion object {
        public const val NAME: String = "mergeChangelog"
    }
}
