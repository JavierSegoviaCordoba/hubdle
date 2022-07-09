@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.config.documentation

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.config.documentation.changelog.ChangelogExtension
import com.javiersc.hubdle.extensions.config.documentation.readme.ReadmeExtension
import com.javiersc.hubdle.extensions.config.documentation.site.SiteExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class DocumentationExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val changelog: ChangelogExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.changelog(action: Action<ChangelogExtension> = Action {}) {
        changelog.run { isEnabled = true }
        action.execute(changelog)
    }

    private val readme: ReadmeExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.readme(action: Action<ReadmeExtension> = Action {}) {
        action.execute(readme)
    }

    private val site: SiteExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.site(action: Action<SiteExtension> = Action {}) {
        site.run { isEnabled = true }
        action.execute(site)
    }
}
