package com.javiersc.hubdle.extensions.config.documentation

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.readme.ReadmeBadgesExtension
import com.javiersc.hubdle.extensions.config.documentation.site.SiteExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

public abstract class DocumentationExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    public fun Project.changelog() {
        pluginManager.apply(PluginIds.Documentation.changelog)

        hubdleState.config.documentation.changelog.isEnabled = true
    }

    private val readmeBadges: ReadmeBadgesExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.readmeBadges(action: Action<ReadmeBadgesExtension> = Action {}) {
        action.execute(readmeBadges)
        hubdleState.config.apply {
            documentation.readmeBadges.isEnabled = true
            documentation.readmeBadges.kotlin = readmeBadges.kotlin
            documentation.readmeBadges.mavenCentral = readmeBadges.mavenCentral
            documentation.readmeBadges.snapshots = readmeBadges.snapshots
            documentation.readmeBadges.build = readmeBadges.build
            documentation.readmeBadges.coverage = readmeBadges.coverage
            documentation.readmeBadges.quality = readmeBadges.quality
            documentation.readmeBadges.techDebt = readmeBadges.techDebt
        }
    }

    private val site: SiteExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.site(action: Action<SiteExtension> = Action {}) {
        pluginManager.apply(PluginIds.Documentation.mkdocs)
        pluginManager.apply(PluginIds.Kotlin.dokka)

        action.execute(site)

        hubdleState.config.documentation.site.isEnabled = true
    }
}
