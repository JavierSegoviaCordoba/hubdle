package com.javiersc.hubdle.project.extensions.config.documentation.site.analysis

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.analysis.HubdleConfigAnalysisExtension.Qodana
import com.javiersc.hubdle.project.extensions.config.analysis.tools.hubdleSonar
import com.javiersc.hubdle.project.extensions.config.documentation.site.BuildSiteTask
import com.javiersc.hubdle.project.extensions.config.documentation.site.PreBuildSiteTask
import com.javiersc.hubdle.project.extensions.config.documentation.site.hubdleSite
import com.javiersc.hubdle.project.extensions.config.documentation.site.projectsInfo
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskProvider

public open class HubdleConfigDocumentationSiteAnalysisExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleSite)

    public val qodana: Property<Boolean> = property { true }

    public val qodanaProjectKey: Property<String?> = property {
        getStringProperty(Qodana.projectKey).orNull
    }

    public val sonar: Property<Boolean> = property { true }

    override fun Project.defaultConfiguration() {
        lazyConfigurable {
            val analysis = this@HubdleConfigDocumentationSiteAnalysisExtension
            val preBuildDocsTask: TaskProvider<PreBuildSiteTask> =
                PreBuildSiteTask.register(project) {
                    projectsInfo.set(project.projectsInfo)
                    qodana.set(analysis.qodana)
                    qodanaProjectKey.set(analysis.qodanaProjectKey)
                    sonar.set(analysis.sonar)
                    sonarProjectId.set(hubdleSonar.projectKey)
                }

            BuildSiteTask.register(project, preBuildDocsTask)
        }
    }
}
