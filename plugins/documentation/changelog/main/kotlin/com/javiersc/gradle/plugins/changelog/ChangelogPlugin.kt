package com.javiersc.gradle.plugins.changelog

import com.javiersc.gradle.plugins.changelog.tasks.AddChangelogItemTask
import com.javiersc.gradle.plugins.changelog.tasks.FormatChangelogTask
import com.javiersc.gradle.plugins.changelog.tasks.MergeChangelogTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.date
import org.jetbrains.changelog.tasks.PatchChangelogTask

abstract class ChangelogPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.changelog")
        target.extensions.findByType<ChangelogPluginExtension>()?.apply {
            version.set("${target.version}")
            header.set(target.provider { "[${version.get()}] - ${date()}" })
            groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated"))
        }

        target.tasks.named<PatchChangelogTask>("patchChangelog").configure {
            it.finalizedBy(FormatChangelogTask.name)
        }

        target.tasks.register<FormatChangelogTask>(FormatChangelogTask.name)

        target.tasks.register<MergeChangelogTask>(MergeChangelogTask.name)

        target.tasks.register<AddChangelogItemTask>(AddChangelogItemTask.name).configure {
            it.finalizedBy(FormatChangelogTask.name)
        }
    }
}
