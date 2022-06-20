package com.javiersc.hubdle.extensions.config.documentation.readme

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class ReadmeExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val badges: ReadmeBadgesExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.badges(action: Action<ReadmeBadgesExtension> = Action {}) {
        badges.isEnabled = true
        action.execute(badges)
        hubdleState.config.documentation.readme.badges.isEnabled = badges.isEnabled
        hubdleState.config.documentation.readme.badges.kotlin = badges.kotlin
        hubdleState.config.documentation.readme.badges.mavenCentral = badges.mavenCentral
        hubdleState.config.documentation.readme.badges.snapshots = badges.snapshots
        hubdleState.config.documentation.readme.badges.build = badges.build
        hubdleState.config.documentation.readme.badges.coverage = badges.coverage
        hubdleState.config.documentation.readme.badges.quality = badges.quality
        hubdleState.config.documentation.readme.badges.techDebt = badges.techDebt
    }
}
