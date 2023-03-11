package com.javiersc.hubdle.project.extensions.config.documentation.site.analysis

import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.documentation.site.hubdleSite
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleConfigDocumentationSiteAnalysisExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleSite)

    public val qodana: Property<Boolean> = property { true }
    public val sonar: Property<Boolean> = property { true }
}
