package com.javiersc.hubdle.extensions._internal.state.config

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.install.commit.InstallAllTestsPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallApplyFormatPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallAssemblePreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallCheckAnalysisPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallCheckApiPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallCheckFormatPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.WriteFilePreCommitTask
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.kotlin.dsl.apply

internal fun configureInstall(project: Project) {
    if (project.hubdleState.config.install.isEnabled) {
        project.pluginManager.apply(BasePlugin::class)

        InstallPreCommitTask.register(project)
        WriteFilePreCommitTask.register(project)

        val preCommitsState = project.hubdleState.config.install.preCommits

        if (preCommitsState.allTests) InstallAllTestsPreCommitTask.register(project)
        if (preCommitsState.applyFormat) InstallApplyFormatPreCommitTask.register(project)
        if (preCommitsState.assemble) InstallAssemblePreCommitTask.register(project)
        if (preCommitsState.checkAnalysis) InstallCheckAnalysisPreCommitTask.register(project)
        if (preCommitsState.checkApi) InstallCheckApiPreCommitTask.register(project)
        if (preCommitsState.checkFormat) InstallCheckFormatPreCommitTask.register(project)
    }
}
