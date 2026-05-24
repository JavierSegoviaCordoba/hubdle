@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation.readme

import hubdle.ecosystem.feature.documentation.HubdleDocumentationDefinition
import hubdle.ecosystem.feature.documentation.readme.badges.WriteReadmeBadgesTask
import hubdle.platform.HubdleProperties.Pom
import hubdle.platform.HubdleProperties.Project
import hubdle.platform.HubdleProperties.Sonar
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction
import org.gradle.kotlin.dsl.register

internal abstract class HubdleDocumentationReadmeApplyAction :
    ProjectFeatureApplyAction<
        HubdleDocumentationReadmeDefinition,
        HubdleDocumentationReadmeBuildModel,
        HubdleDocumentationDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleDocumentationReadmeDefinition,
        buildModel: HubdleDocumentationReadmeBuildModel,
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
            registerReadmeBadgesTask()
        }
    }

    private fun HubdleDocumentationReadmeDefinition.registerReadmeBadgesTask() {
        if (!badges.enabled.orElse(true).get()) return

        fun prop(name: String) = project.providers.gradleProperty(name)

        project.tasks.register<WriteReadmeBadgesTask>(WriteReadmeBadgesTask.NAME).configure {
            it.buildBadge.set(badges.build.orElse(true))
            it.coverageBadge.set(badges.coverage.orElse(true))
            it.kotlinBadge.set(badges.kotlin.orElse(true))
            it.kotlinVersion.set(prop("kotlinVersion").orElse("unknown"))
            it.mavenCentralBadge.set(badges.mavenCentral.orElse(true))
            it.projectGroup.set(prop(Project.Group).orElse(project.group.toString()))
            it.projectKey.set(prop(Sonar.ProjectKey).orElse(""))
            it.projectName.set(
                badges.mainProjectName.orElse(prop(Project.Name).orElse(project.name))
            )
            it.qualityBadge.set(badges.quality.orElse(true))
            it.repoUrl.set(prop(Pom.Scm.Url).orElse(""))
            it.snapshotsBadge.set(badges.snapshots.orElse(true))
            it.techDebtBadge.set(badges.techDebt.orElse(true))
        }
    }

    companion object {
        const val NAME: String = "readme"
    }
}
