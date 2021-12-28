package com.javiersc.gradle.plugins.changelog

import com.javiersc.gradle.plugins.changelog.tasks.AddChangelogItem
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

        target.tasks.apply {
            named<PatchChangelogTask>("patchChangelog") { finalizedBy(FormatChangelogTask.name) }
            register<FormatChangelogTask>(FormatChangelogTask.name)

            register<MergeChangelogTask>(MergeChangelogTask.name)

            register<AddChangelogItem>(AddChangelogItem.name) {
                finalizedBy(FormatChangelogTask.name)
            }
        }
    }
}
