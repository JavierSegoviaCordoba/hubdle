package com.javiersc.hubdle.project.extensions.config.documentation.readme

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.documentation.hubdleDocumentation
import com.javiersc.hubdle.project.extensions.config.documentation.readme.badges.HubdleConfigDocumentationReadmeBadgesExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleConfigDocumentationReadmeExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleDocumentation)

    public val badges: HubdleConfigDocumentationReadmeBadgesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun badges(action: Action<HubdleConfigDocumentationReadmeBadgesExtension> = Action {}) {
        badges.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleReadme: HubdleConfigDocumentationReadmeExtension
    get() = getHubdleExtension()
