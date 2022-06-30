package com.javiersc.hubdle.extensions.config.documentation.changelog._internal

import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.changelog.AddChangelogItemTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.ApplyFormatChangelogTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.MergeChangelogTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.date
import org.jetbrains.changelog.tasks.PatchChangelogTask

internal fun configureChangelog(project: Project) {
    if (project.hubdleState.config.documentation.changelog.isEnabled) {
        project.pluginManager.apply(PluginIds.Documentation.changelog)

        project.extensions.findByType<ChangelogPluginExtension>()?.apply {
            version.set("${project.version}")
            header.set(project.provider { "[${version.get()}] - ${date()}" })
            groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated"))
        }

        project.tasks.namedLazily<PatchChangelogTask>("patchChangelog").configureEach {
            it.finalizedBy(ApplyFormatChangelogTask.name)
        }

        project.tasks.register<ApplyFormatChangelogTask>(ApplyFormatChangelogTask.name)

        project.tasks.register<MergeChangelogTask>(MergeChangelogTask.name)

        project.tasks.register<AddChangelogItemTask>(AddChangelogItemTask.name).configure {
            it.finalizedBy(ApplyFormatChangelogTask.name)
        }
    }
}

internal fun configureConfigDocumentationChangelogRawConfig(project: Project) {
    project.hubdleState.config.documentation.changelog.rawConfig.changelog?.execute(project.the())
}
