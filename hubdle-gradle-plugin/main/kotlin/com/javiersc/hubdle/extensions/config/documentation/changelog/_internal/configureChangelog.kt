package com.javiersc.hubdle.extensions.config.documentation.changelog._internal

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.changelog.AddChangelogItemTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.ApplyFormatChangelogTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.GenerateChangelogHtmlTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.MergeChangelogTask
import org.gradle.api.Project
import org.gradle.api.attributes.LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.ChangelogSectionUrlBuilder
import org.jetbrains.changelog.tasks.PatchChangelogTask

internal fun configureChangelog(project: Project) {
    if (project.hubdleState.config.documentation.changelog.isEnabled) {
        project.pluginManager.apply(PluginIds.Documentation.changelog)

        project.configure<ChangelogPluginExtension> {
            repositoryUrl.set(project.getStringProperty(HubdleProperty.POM.scmUrl))
            groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated"))
            combinePreReleases.set(false)

            val prefix = project.hubdleState.config.versioning.tagPrefix

            val customSectionUrlBuilder =
                project.provider {
                    @Suppress("ObjectLiteralToLambda")
                    object : ChangelogSectionUrlBuilder {
                        override fun build(
                            repositoryUrl: String,
                            currentVersion: String?,
                            previousVersion: String?,
                            isUnreleased: Boolean
                        ): String {
                            val comparison =
                                when {
                                    isUnreleased -> {
                                        when (previousVersion) {
                                            null -> "/commits"
                                            else -> "/compare/$prefix$previousVersion...HEAD"
                                        }
                                    }
                                    previousVersion == null -> "/commits/$prefix$currentVersion"
                                    else ->
                                        "/compare/$prefix$previousVersion...$prefix$currentVersion"
                                }
                            return repositoryUrl + comparison
                        }
                    }
                }

            sectionUrlBuilder.set(customSectionUrlBuilder)
        }

        project.tasks.register<ApplyFormatChangelogTask>(ApplyFormatChangelogTask.name)

        val patchChangelogTask: TaskCollection<PatchChangelogTask> =
            project.tasks.namedLazily<PatchChangelogTask>("patchChangelog").apply {
                configureEach { task -> task.finalizedBy(ApplyFormatChangelogTask.name) }
            }

        project.tasks.register<MergeChangelogTask>(MergeChangelogTask.name).configure { task ->
            task.shouldRunAfter(patchChangelogTask)
            task.finalizedBy(ApplyFormatChangelogTask.name)
        }

        project.tasks.register<AddChangelogItemTask>(AddChangelogItemTask.name).configure { task ->
            task.finalizedBy(ApplyFormatChangelogTask.name)
        }

        val generateChangelogHtmlTask =
            project.tasks.register<GenerateChangelogHtmlTask>(GenerateChangelogHtmlTask.name)

        generateChangelogHtmlTask.configure { task ->
            val htmlText =
                project.the<ChangelogPluginExtension>().getOrNull("${project.version}")?.toHTML()
            task.html.set(htmlText ?: "Changelog not found")
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
    project.configurations.create("generatedChangelogHtml") { configuration ->
        configuration.isCanBeConsumed = true
        configuration.isCanBeResolved = false
        configuration.attributes { attributes ->
            attributes.attribute(
                LIBRARY_ELEMENTS_ATTRIBUTE,
                project.objects.named(GENERATED_CHANGELOG_HTML_ATTRIBUTE)
            )
        }
        configuration.outgoing { publications -> publications.artifact(generateChangelogHtmlTask) }
    }
}

internal const val GENERATED_CHANGELOG_HTML_ATTRIBUTE = "generated-changelog-html"
