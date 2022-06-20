package com.javiersc.hubdle.extensions.config.install._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.install.commit.InstallAllTestsPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallApplyFormatPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallAssemblePreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallCheckAnalysisPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallCheckApiPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallCheckFormatPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallDumpApiPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.InstallPreCommitTask
import com.javiersc.hubdle.extensions.config.install.commit.WriteFilePreCommitTask
import org.gradle.api.Project

internal fun configureInstallPreCommits(project: Project) {
    val preCommitsState = project.hubdleState.config.install.preCommits
    if (preCommitsState.isEnabled) {

        check(project.isRootProject) {
            "Hubdle `install` must be configured only in the root project"
        }

        InstallPreCommitTask.register(project)
        WriteFilePreCommitTask.register(project)

        if (preCommitsState.allTests) InstallAllTestsPreCommitTask.register(project)
        if (preCommitsState.applyFormat) InstallApplyFormatPreCommitTask.register(project)
        if (preCommitsState.assemble) InstallAssemblePreCommitTask.register(project)
        if (preCommitsState.checkAnalysis) InstallCheckAnalysisPreCommitTask.register(project)
        if (preCommitsState.checkApi) InstallCheckApiPreCommitTask.register(project)
        if (preCommitsState.checkFormat) InstallCheckFormatPreCommitTask.register(project)
        if (preCommitsState.dumpApi) InstallDumpApiPreCommitTask.register(project)
    }
}
