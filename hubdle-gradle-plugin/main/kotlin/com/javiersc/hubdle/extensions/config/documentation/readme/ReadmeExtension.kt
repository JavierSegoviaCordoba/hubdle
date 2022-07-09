package com.javiersc.hubdle.extensions.config.documentation.readme

import com.javiersc.hubdle.extensions.HubdleDslMarker
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
        badges.run { isEnabled = true }
        action.execute(badges)
    }
}
