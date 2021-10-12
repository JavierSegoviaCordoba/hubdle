@file:OptIn(ExperimentalStdlibApi::class)

import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.date
import org.jetbrains.changelog.tasks.PatchChangelogTask

plugins { id("org.jetbrains.changelog") }

configure<ChangelogPluginExtension> {
    version.set("${project.version}")
    header.set(provider { "[${version.get()}] - ${date()}" })
    groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated"))
}

tasks {
    named<PatchChangelogTask>("patchChangelog") {
        group = "changelog"
        doLast { patchChangelog() }
    }

    register("mergeChangelog") {
        group = "changelog"

        doLast { mergeChangelog() }
    }

    register<AddChangelogItem>("addChangelogItem")
}
