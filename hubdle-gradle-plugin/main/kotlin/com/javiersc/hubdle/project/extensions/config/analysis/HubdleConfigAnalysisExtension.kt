package com.javiersc.hubdle.project.extensions.config.analysis

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.analysis.tools.HubdleConfigAnalysisDetektExtension
import com.javiersc.hubdle.project.extensions.config.analysis.tools.HubdleConfigAnalysisSonarExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleConfigAnalysisExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    public val detekt: HubdleConfigAnalysisDetektExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun detekt(action: Action<HubdleConfigAnalysisDetektExtension>) {
        detekt.enableAndExecute(action)
    }

    public val sonar: HubdleConfigAnalysisSonarExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun sonar(action: Action<HubdleConfigAnalysisSonarExtension>) {
        sonar.enableAndExecute(action)
    }

    public object Qodana {
        public const val projectKey: String = "analysis.qodana.projectKey"
    }

    override fun Project.defaultConfiguration() {}
}

internal val HubdleEnableableExtension.hubdleAnalysis: HubdleConfigAnalysisExtension
    get() = getHubdleExtension()
