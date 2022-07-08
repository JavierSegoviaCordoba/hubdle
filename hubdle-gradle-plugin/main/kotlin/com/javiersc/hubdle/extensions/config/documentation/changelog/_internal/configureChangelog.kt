package com.javiersc.hubdle.extensions.config.documentation.changelog._internal

import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.changelog.AddChangelogItemTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.ApplyFormatChangelogTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.GenerateChangelogHtmlTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.MergeChangelogTask
import org.gradle.api.Project
import org.gradle.api.attributes.LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.date
import org.jetbrains.changelog.tasks.PatchChangelogTask

internal fun configureChangelog(project: Project) {
    if (project.hubdleState.config.documentation.changelog.isEnabled) {
        project.pluginManager.apply(PluginIds.Documentation.changelog)

        project.configure<ChangelogPluginExtension> {
            version.set("${project.version}")
            header.set(project.provider { "[${project.version}] - ${date()}" })
            groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated"))
        }

        project.tasks.namedLazily<PatchChangelogTask>("patchChangelog").configureEach {
            finalizedBy(ApplyFormatChangelogTask.name)
        }

        project.tasks.register<ApplyFormatChangelogTask>(ApplyFormatChangelogTask.name)

        project.tasks.register<MergeChangelogTask>(MergeChangelogTask.name)

        project.tasks.register<AddChangelogItemTask>(AddChangelogItemTask.name).configure {
            finalizedBy(ApplyFormatChangelogTask.name)
        }

        val generateChangelogHtmlTask =
            project.tasks.register<GenerateChangelogHtmlTask>(GenerateChangelogHtmlTask.name)

        generateChangelogHtmlTask.configure {
            val htmlText =
                project.the<ChangelogPluginExtension>().getOrNull("${project.version}")?.toHTML()
            html.set(htmlText ?: "Changelog not found")
        }

        createGeneratedChangelogHtmlConfiguration(project, generateChangelogHtmlTask)
    }
}

internal fun configureConfigDocumentationChangelogRawConfig(project: Project) {
    project.hubdleState.config.documentation.changelog.rawConfig.changelog?.execute(project.the())
}

private fun createGeneratedChangelogHtmlConfiguration(
    project: Project,
    generateChangelogHtmlTask: TaskProvider<GenerateChangelogHtmlTask>,
) {
    project.configurations.create("generatedChangelogHtml") {
        isCanBeConsumed = true
        isCanBeResolved = false
        attributes {
            attribute(
                LIBRARY_ELEMENTS_ATTRIBUTE,
                project.objects.named(GENERATED_CHANGELOG_HTML_ATTRIBUTE)
            )
        }
        outgoing { artifact(generateChangelogHtmlTask) }
    }
}

internal const val GENERATED_CHANGELOG_HTML_ATTRIBUTE = "generated-changelog-html"
