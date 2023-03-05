package com.javiersc.hubdle.project.extensions.config.documentation.changelog

import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

public abstract class GenerateChangelogHtmlTask
@Inject
constructor(
    objects: ObjectFactory,
    layout: ProjectLayout,
) : DefaultTask() {

    @get:Input public abstract val html: Property<String?>

    @OutputFile
    public val htmlFile: RegularFileProperty =
        objects
            .fileProperty()
            .convention(layout.buildDirectory.file(GENERATED_CHANGELOG_HTML_FILE_PATH))

    @TaskAction
    public fun run() {
        htmlFile.get().asFile.apply {
            parentFile.mkdirs()
            createNewFile()
            html.orNull?.let(::writeText)
        }
    }

    public companion object {
        public const val NAME: String = "generateChangelogHtml"
    }
}

internal const val GENERATED_CHANGELOG_HTML_DIR_PATH = "generated/changelog"
internal const val GENERATED_CHANGELOG_HTML_FILE_PATH =
    "$GENERATED_CHANGELOG_HTML_DIR_PATH/CHANGELOG.html"
