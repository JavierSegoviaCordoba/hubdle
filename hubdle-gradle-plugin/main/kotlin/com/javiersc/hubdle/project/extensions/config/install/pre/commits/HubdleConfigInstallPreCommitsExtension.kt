package com.javiersc.hubdle.project.extensions.config.install.pre.commits

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.install.hubdleInstall
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.InstallAllTestsPreCommitTask
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.InstallApplyFormatPreCommitTask
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.InstallAssemblePreCommitTask
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.InstallCheckAnalysisPreCommitTask
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.InstallCheckApiPreCommitTask
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.InstallCheckFormatPreCommitTask
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.InstallDumpApiPreCommitTask
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.InstallPreCommitTask
import com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks.WriteFilePreCommitTask
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleConfigInstallPreCommitsExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleInstall)

    override val priority: Priority = Priority.P3

    public val allTests: Property<Boolean> = property { false }
    public val applyFormat: Property<Boolean> = property { false }
    public val assemble: Property<Boolean> = property { false }
    public val checkAnalysis: Property<Boolean> = property { false }
    public val checkApi: Property<Boolean> = property { false }
    public val checkFormat: Property<Boolean> = property { false }
    public val dumpApi: Property<Boolean> = property { false }

    override fun Project.defaultConfiguration() {
        configurable {
            check(isRootProject) { "Hubdle `install` must be configured only in the root project" }

            InstallPreCommitTask.register(project)
            WriteFilePreCommitTask.register(project)

            if (allTests.get()) InstallAllTestsPreCommitTask.register(project)
            if (applyFormat.get()) InstallApplyFormatPreCommitTask.register(project)
            if (assemble.get()) InstallAssemblePreCommitTask.register(project)
            if (checkAnalysis.get()) InstallCheckAnalysisPreCommitTask.register(project)
            if (checkApi.get()) InstallCheckApiPreCommitTask.register(project)
            if (checkFormat.get()) InstallCheckFormatPreCommitTask.register(project)
            if (dumpApi.get()) InstallDumpApiPreCommitTask.register(project)
        }
    }
}

internal val HubdleEnableableExtension.hubdlePreCommits: HubdleConfigInstallPreCommitsExtension
    get() = hubdleInstall.preCommits
