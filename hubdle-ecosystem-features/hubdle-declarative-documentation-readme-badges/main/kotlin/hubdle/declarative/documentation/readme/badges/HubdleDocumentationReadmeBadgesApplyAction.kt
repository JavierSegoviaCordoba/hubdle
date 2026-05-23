@file:Suppress("UnstableApiUsage")

package hubdle.declarative.documentation.readme.badges

import hubdle.declarative.documentation.readme.HubdleDocumentationReadmeDefinition
import hubdle.platform.HubdleProperties.Pom
import hubdle.platform.HubdleProperties.Project
import hubdle.platform.HubdleProperties.Sonar
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction
import org.gradle.kotlin.dsl.register

internal abstract class HubdleDocumentationReadmeBadgesApplyAction :
    ProjectFeatureApplyAction<
        HubdleDocumentationReadmeBadgesDefinition,
        HubdleDocumentationReadmeBadgesBuildModel,
        HubdleDocumentationReadmeDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleDocumentationReadmeBadgesDefinition,
        buildModel: HubdleDocumentationReadmeBadgesBuildModel,
        parentDefinition: HubdleDocumentationReadmeDefinition,
    ) = definition.run {
        val featureEffectiveEnabled =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }
            project.registerWriteReadmeBadgesTask(definition)
        }
    }

    private fun org.gradle.api.Project.registerWriteReadmeBadgesTask(
        definition: HubdleDocumentationReadmeBadgesDefinition
    ) = definition.run {
        fun prop(name: String) = providers.gradleProperty(name)

        tasks.register<WriteReadmeBadgesTask>(WriteReadmeBadgesTask.NAME).configure { task ->
            task.projectGroup.set(prop(Project.Group).orElse(group.toString()))
            task.projectName.set(mainProjectName.orElse(prop(Project.Name).orElse(name)))
            task.repoUrl.set(prop(Pom.Scm.Url).orElse(""))
            task.kotlinVersion.set(prop("kotlinVersion").orElse("unknown"))
            task.projectKey.set(prop(Sonar.ProjectKey).orElse(""))
            task.kotlinBadge.set(kotlin.orElse(true))
            task.mavenCentralBadge.set(mavenCentral.orElse(true))
            task.snapshotsBadge.set(snapshots.orElse(true))
            task.buildBadge.set(build.orElse(true))
            task.coverageBadge.set(coverage.orElse(true))
            task.qualityBadge.set(quality.orElse(true))
            task.techDebtBadge.set(techDebt.orElse(true))
        }
    }

    companion object {
        const val NAME: String = "badges"
    }
}
