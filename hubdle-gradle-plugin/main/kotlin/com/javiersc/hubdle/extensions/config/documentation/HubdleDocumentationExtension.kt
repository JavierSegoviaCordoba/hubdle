@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.config.documentation

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.config.documentation.changelog.HubdleChangelogExtension
import com.javiersc.hubdle.extensions.config.documentation.readme.HubdleReadmeExtension
import com.javiersc.hubdle.extensions.config.documentation.site.HubdleSiteExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class HubdleDocumentationExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val changelog: HubdleChangelogExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.changelog(action: Action<HubdleChangelogExtension> = Action {}) {
        changelog.run { isEnabled = true }
        action.execute(changelog)
    }

    private val readme: HubdleReadmeExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.readme(action: Action<HubdleReadmeExtension> = Action {}) {
        action.execute(readme)
    }

    private val site: HubdleSiteExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.site(action: Action<HubdleSiteExtension> = Action {}) {
        site.run { isEnabled = true }
        action.execute(site)
    }
}
