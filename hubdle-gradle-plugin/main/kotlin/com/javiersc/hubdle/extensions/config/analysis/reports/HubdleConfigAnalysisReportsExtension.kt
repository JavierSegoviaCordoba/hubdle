package com.javiersc.hubdle.extensions.config.analysis.reports

import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.analysis.hubdleAnalysis
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleConfigAnalysisReportsExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAnalysis)

    public val md: Property<Boolean> = property { true }

    public val html: Property<Boolean> = property { true }

    public val sarif: Property<Boolean> = property { true }

    public val txt: Property<Boolean> = property { false }

    public val xml: Property<Boolean> = property { true }
}
