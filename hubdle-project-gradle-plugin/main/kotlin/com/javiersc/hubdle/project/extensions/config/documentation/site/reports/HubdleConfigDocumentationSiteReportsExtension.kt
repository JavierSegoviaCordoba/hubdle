package com.javiersc.hubdle.project.extensions.config.documentation.site.reports

import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.documentation.site.hubdleSite
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleConfigDocumentationSiteReportsExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleSite)

    public val allTests: Property<Boolean> = property { true }
    public val codeAnalysis: Property<Boolean> = property { true }
    public val codeCoverage: Property<Boolean> = property { true }
    public val codeQuality: Property<Boolean> = property { true }
}
