package com.javiersc.hubdle.extensions.config.documentation

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.config.documentation.api.HubdleConfigDocumentationApiExtension
import com.javiersc.hubdle.extensions.config.documentation.changelog.HubdleConfigDocumentationChangelogExtension
import com.javiersc.hubdle.extensions.config.documentation.readme.HubdleConfigDocumentationReadmeExtension
import com.javiersc.hubdle.extensions.config.documentation.site.HubdleConfigDocumentationSiteExtension
import com.javiersc.hubdle.extensions.config.hubdleConfig
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleConfigDocumentationExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    public val api: HubdleConfigDocumentationApiExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun api(action: Action<HubdleConfigDocumentationApiExtension> = Action {}) {
        api.enableAndExecute(action)
    }

    public val changelog: HubdleConfigDocumentationChangelogExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun changelog(action: Action<HubdleConfigDocumentationChangelogExtension> = Action {}) {
        changelog.enableAndExecute(action)
    }

    public val readme: HubdleConfigDocumentationReadmeExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun readme(action: Action<HubdleConfigDocumentationReadmeExtension> = Action {}) {
        readme.enableAndExecute(action)
    }

    public val site: HubdleConfigDocumentationSiteExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun site(action: Action<HubdleConfigDocumentationSiteExtension> = Action {}) {
        site.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleDocumentation: HubdleConfigDocumentationExtension
    get() = getHubdleExtension()
