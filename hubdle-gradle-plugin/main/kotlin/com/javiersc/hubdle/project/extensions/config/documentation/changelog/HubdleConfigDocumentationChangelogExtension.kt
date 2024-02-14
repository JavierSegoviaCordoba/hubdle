package com.javiersc.hubdle.project.extensions.config.documentation.changelog

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.documentation.hubdleDocumentation
import com.javiersc.hubdle.project.extensions.config.publishing.maven.hubdlePublishingMavenPom
import com.javiersc.hubdle.project.extensions.config.versioning.semver._internal.isTagPrefixProject
import com.javiersc.hubdle.project.extensions.config.versioning.semver.hubdleSemver
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.tasks.BaseChangelogTask
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

    @HubdleDslMarker
    public fun changelog(action: Action<ChangelogPluginExtension>) {
        configurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsChangelog)

        configurable {
            configure<ChangelogPluginExtension> {
                repositoryUrl.set(hubdlePublishingMavenPom.scmUrl)
                groups.set(
                    listOf(
                        "Added",
                        "Changed",
                        "Deprecated",
                        "Fixed",
                        "Removed",
                        "Updated",
                    )
                )
                combinePreReleases.set(false)

                versionPrefix.set(hubdleSemver.tagPrefix)
            }

            tasks.register<ApplyFormatChangelogTask>(ApplyFormatChangelogTask.NAME)

            tasks.withType<BaseChangelogTask>().configureEach { task ->
                task.enabled = isTagPrefixProject
            }

            val patchChangelogTask: TaskProvider<PatchChangelogTask> =
                tasks.named<PatchChangelogTask>("patchChangelog")

            patchChangelogTask.configure { task ->
                task.enabled = isTagPrefixProject
                task.finalizedBy(ApplyFormatChangelogTask.NAME)
            }

            tasks.register<MergeChangelogTask>(MergeChangelogTask.NAME).configure { task ->
                task.enabled = isTagPrefixProject
                task.mustRunAfter(patchChangelogTask)
                task.finalizedBy(ApplyFormatChangelogTask.NAME)
            }

            tasks.register<AddChangelogItemTask>(AddChangelogItemTask.NAME).configure { task ->
                task.enabled = isTagPrefixProject
                task.finalizedBy(ApplyFormatChangelogTask.NAME)
            }

            val generateChangelogHtmlTask: TaskProvider<GenerateChangelogHtmlTask> =
                tasks.register<GenerateChangelogHtmlTask>(GenerateChangelogHtmlTask.NAME)

            generateChangelogHtmlTask.configure { task ->
                task.enabled = isTagPrefixProject
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
