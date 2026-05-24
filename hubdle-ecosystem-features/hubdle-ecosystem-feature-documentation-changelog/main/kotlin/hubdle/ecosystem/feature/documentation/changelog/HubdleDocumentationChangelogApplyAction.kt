@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation.changelog

import hubdle.ecosystem.feature.documentation.HubdleDocumentationDefinition
import hubdle.platform.HubdleProperties
import hubdle.platform.HubdleServices
import hubdle.platform.PluginIds
import hubdle.platform.applyPlugin
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.api.Project
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.tasks.TaskProvider
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.tasks.BaseChangelogTask
import org.jetbrains.changelog.tasks.PatchChangelogTask

internal abstract class HubdleDocumentationChangelogApplyAction :
    ProjectFeatureApplyAction<
        HubdleDocumentationChangelogDefinition,
        HubdleDocumentationChangelogBuildModel,
        HubdleDocumentationDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleDocumentationChangelogDefinition,
        buildModel: HubdleDocumentationChangelogBuildModel,
        parentDefinition: HubdleDocumentationDefinition,
    ) = definition.run {
        val featureEffectiveEnabled =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }
            configureChangelog()
        }
    }

    private fun HubdleDocumentationChangelogDefinition.configureChangelog() {
        applyPlugin(PluginIds.JetbrainsChangelog)

        project.configure<ChangelogPluginExtension> {
            repositoryUrl.set(
                this@configureChangelog.repositoryUrl.orElse(
                    project.providers.gradleProperty(HubdleProperties.Pom.Scm.Url).orElse("")
                )
            )
            groups.set(
                this@configureChangelog.groups
                    .map { groups -> groups.ifEmpty { DefaultGroups } }
                    .orElse(DefaultGroups)
            )
            combinePreReleases.set(this@configureChangelog.combinePreReleases.orElse(false))
            versionPrefix.set(
                this@configureChangelog.versionPrefix.orElse(
                    project.providers.gradleProperty("semver.tagPrefix").orElse("")
                )
            )
        }

        project.tasks.register<ApplyFormatChangelogTask>(ApplyFormatChangelogTask.NAME)
        project.tasks.withType<BaseChangelogTask>().configureEach { task -> task.enabled = true }

        project.plugins.withId(PluginIds.JetbrainsChangelog.id) {
            val patchChangelogTask: TaskProvider<PatchChangelogTask> =
                project.tasks.named<PatchChangelogTask>("patchChangelog")

            patchChangelogTask.configure { task ->
                task.enabled = true
                task.finalizedBy(ApplyFormatChangelogTask.NAME)
            }

            project.tasks.register<MergeChangelogTask>(MergeChangelogTask.NAME).configure { task ->
                task.enabled = true
                task.mustRunAfter(patchChangelogTask)
                task.finalizedBy(ApplyFormatChangelogTask.NAME)
            }

            project.tasks
                .register<AddChangelogItemTask>(AddChangelogItemTask.NAME, project.rootDir)
                .configure { task ->
                    task.enabled = true
                    task.finalizedBy(ApplyFormatChangelogTask.NAME)
                }

            val generateChangelogHtmlTask: TaskProvider<GenerateChangelogHtmlTask> =
                project.tasks.register<GenerateChangelogHtmlTask>(GenerateChangelogHtmlTask.NAME)

            generateChangelogHtmlTask.configure { task ->
                task.enabled = true
                val changelogExt = project.the<ChangelogPluginExtension>()
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
                    project.objects.named(GENERATED_CHANGELOG_HTML_ATTRIBUTE),
                )
            }
            configuration.outgoing { publications ->
                publications.artifact(generateChangelogHtmlTask)
            }
        }
    }

    companion object {
        const val GENERATED_CHANGELOG_HTML_ATTRIBUTE: String = "generated-changelog-html"
        const val NAME: String = "changelog"

        val DefaultGroups: List<String> =
            listOf("Added", "Changed", "Deprecated", "Fixed", "Removed", "Updated")
    }
}
