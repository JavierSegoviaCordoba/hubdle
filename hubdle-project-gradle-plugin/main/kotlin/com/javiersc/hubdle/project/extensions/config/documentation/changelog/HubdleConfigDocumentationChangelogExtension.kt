package com.javiersc.hubdle.project.extensions.config.documentation.changelog

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.HubdleProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.documentation.hubdleDocumentation
import com.javiersc.hubdle.project.extensions.config.versioning.hubdleVersioning
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.ChangelogSectionUrlBuilder
import org.jetbrains.changelog.tasks.PatchChangelogTask

@HubdleDslMarker
public open class HubdleConfigDocumentationChangelogExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleDocumentation)

    override val priority: Priority = Priority.P3

    @HubdleDslMarker
    public fun changelog(action: Action<ChangelogPluginExtension>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsChangelog
        )

        configurable {
            configure<ChangelogPluginExtension> {
                repositoryUrl.set(project.getStringProperty(HubdleProperty.POM.scmUrl))
                groups.set(
                    listOf(
                        "Added",
                        "Changed",
                        "Deprecated",
                        "Removed",
                        "Fixed",
                        "Updated",
                    )
                )
                combinePreReleases.set(false)

                val prefix = hubdleVersioning.tagPrefix.get()

                val customSectionUrlBuilder = provider {
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

            tasks.register<ApplyFormatChangelogTask>(ApplyFormatChangelogTask.NAME)

            val patchChangelogTask: TaskCollection<PatchChangelogTask> =
                tasks.namedLazily<PatchChangelogTask>("patchChangelog").apply {
                    configureEach { task -> task.finalizedBy(ApplyFormatChangelogTask.NAME) }
                }

            tasks.register<MergeChangelogTask>(MergeChangelogTask.NAME).configure { task ->
                task.mustRunAfter(patchChangelogTask)
                task.finalizedBy(ApplyFormatChangelogTask.NAME)
            }

            tasks.register<AddChangelogItemTask>(AddChangelogItemTask.NAME).configure { task ->
                task.finalizedBy(ApplyFormatChangelogTask.NAME)
            }

            val generateChangelogHtmlTask =
                tasks.register<GenerateChangelogHtmlTask>(GenerateChangelogHtmlTask.NAME)

            generateChangelogHtmlTask.configure { task ->
                val changelogExt = the<ChangelogPluginExtension>()
                val htmlText =
                    changelogExt.getOrNull("${project.version}")?.let { item ->
                        changelogExt.renderItem(item, Changelog.OutputType.HTML)
                    }
                task.html.set(htmlText ?: "Changelog not found")
            }

            createGeneratedChangelogHtmlConfiguration(project, generateChangelogHtmlTask)
        }
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
                    LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE,
                    project.objects.named(GENERATED_CHANGELOG_HTML_ATTRIBUTE)
                )
            }
            configuration.outgoing { publications ->
                publications.artifact(generateChangelogHtmlTask)
            }
        }
    }

    public companion object {
        internal const val GENERATED_CHANGELOG_HTML_ATTRIBUTE = "generated-changelog-html"
    }
}

internal val HubdleEnableableExtension.hubdleChangelog: HubdleConfigDocumentationChangelogExtension
    get() = getHubdleExtension()
