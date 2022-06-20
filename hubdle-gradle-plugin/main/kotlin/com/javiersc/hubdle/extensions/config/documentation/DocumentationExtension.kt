@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.config.documentation

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
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
        changelog.isEnabled = true
        action.execute(changelog)
        hubdleState.config.documentation.changelog.isEnabled = changelog.isEnabled
    }

    private val readme: ReadmeExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.readme(action: Action<ReadmeExtension> = Action {}) {
        action.execute(readme)
    }

    private val site: SiteExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.site(action: Action<SiteExtension> = Action {}) {
        site.isEnabled = true
        action.execute(site)
        hubdleState.config.documentation.site.isEnabled = site.isEnabled
    }
}
