@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.install

import hubdle.ecosystem.feature.install.tasks.InstallApplyFormatPreCommitTask
import hubdle.ecosystem.feature.install.tasks.InstallAssemblePreCommitTask
import hubdle.ecosystem.feature.install.tasks.InstallCheckAnalysisPreCommitTask
import hubdle.ecosystem.feature.install.tasks.InstallCheckApiPreCommitTask
import hubdle.ecosystem.feature.install.tasks.InstallCheckFormatPreCommitTask
import hubdle.ecosystem.feature.install.tasks.InstallDumpApiPreCommitTask
import hubdle.ecosystem.feature.install.tasks.InstallPreCommitTask
import hubdle.ecosystem.feature.install.tasks.InstallTestsPreCommitTask
import hubdle.ecosystem.feature.install.tasks.WriteFilePreCommitTask
import hubdle.platform.HubdleEcosystemDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleInstallApplyAction :
    ProjectFeatureApplyAction<
        HubdleInstallDefinition,
        HubdleInstallBuildModel,
        HubdleEcosystemDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleInstallDefinition,
        buildModel: HubdleInstallBuildModel,
        parentDefinition: HubdleEcosystemDefinition,
    ) = definition.run {
        val featureEffectiveEnabled =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }
            registerPreCommitsTasks()
        }
    }

    private fun HubdleInstallDefinition.registerPreCommitsTasks() {
        if (!preCommits.isEnabled()) return
        check(project == project.rootProject) {
            "Hubdle `install` must be configured only in the root project"
        }

        InstallPreCommitTask.register(project)
        WriteFilePreCommitTask.register(project)
        preCommits.registerOptionalTasks(project)
    }

    private fun HubdleInstallPreCommitsDefinition.isEnabled(): Boolean =
        enabled.orElse(false).get() ||
            tests.orElse(false).get() ||
            applyFormat.orElse(false).get() ||
            assemble.orElse(false).get() ||
            checkAnalysis.orElse(false).get() ||
            checkApi.orElse(false).get() ||
            checkFormat.orElse(false).get() ||
            dumpApi.orElse(false).get()

    private fun HubdleInstallPreCommitsDefinition.registerOptionalTasks(
        project: org.gradle.api.Project
    ) {
        if (tests.orElse(false).get()) InstallTestsPreCommitTask.register(project)
        if (applyFormat.orElse(false).get()) InstallApplyFormatPreCommitTask.register(project)
        if (assemble.orElse(false).get()) InstallAssemblePreCommitTask.register(project)
        if (checkAnalysis.orElse(false).get()) InstallCheckAnalysisPreCommitTask.register(project)
        if (checkApi.orElse(false).get()) InstallCheckApiPreCommitTask.register(project)
        if (checkFormat.orElse(false).get()) InstallCheckFormatPreCommitTask.register(project)
        if (dumpApi.orElse(false).get()) InstallDumpApiPreCommitTask.register(project)
    }

    companion object {
        const val NAME: String = "install"
    }
}
