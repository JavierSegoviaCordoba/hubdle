package com.javiersc.hubdle.extensions._internal.state.config.documentation

import com.javiersc.gradle.extensions.namedLazily
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.changelog.tasks.AddChangelogItemTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.tasks.FormatChangelogTask
import com.javiersc.hubdle.extensions.config.documentation.changelog.tasks.MergeChangelogTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.register
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.date
import org.jetbrains.changelog.tasks.PatchChangelogTask

internal fun configureChangelog(project: Project) {
    if (hubdleState.config.documentation.changelog.isEnabled) {
        project.pluginManager.apply(PluginIds.Documentation.changelog)

        project.extensions.findByType<ChangelogPluginExtension>()?.apply {
            version.set("${project.version}")
            header.set(project.provider { "[${version.get()}] - ${date()}" })
            groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated"))
        }

        project.tasks.namedLazily<PatchChangelogTask>("patchChangelog").configureEach {
            it.finalizedBy(FormatChangelogTask.name)
        }

        project.tasks.register<FormatChangelogTask>(FormatChangelogTask.name)

        project.tasks.register<MergeChangelogTask>(MergeChangelogTask.name)

        project.tasks.register<AddChangelogItemTask>(AddChangelogItemTask.name).configure {
            it.finalizedBy(FormatChangelogTask.name)
        }
    }
}
