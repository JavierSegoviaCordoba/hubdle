package com.javiersc.hubdle.extensions.config.documentation.site

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.config.documentation.hubdleDocumentation
import com.javiersc.hubdle.extensions.config.documentation.site.reports.HubdleConfigDocumentationSiteReportsExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

@HubdleDslMarker
public open class HubdleConfigDocumentationSiteExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleDocumentation)

    override val priority: Priority = Priority.P3

    public val reports: HubdleConfigDocumentationSiteReportsExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun reports(action: Action<HubdleConfigDocumentationSiteReportsExtension>) {
        reports.enableAndExecute(action)
    }

    @HubdleDslMarker
    public fun mkdocs(action: Action<MkdocsExtension>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.VyarusMkdocsBuild
        )
        configurable {
            check(isRootProject) {
                """
                    |`site` can be only used in the root project, and it is being applied in:
                    |  - `${displayName}`
                """
                    .trimMargin()
            }
            configure<MkdocsExtension> {
                strict = false
                sourcesDir = "${rootProject.rootDir}/build/.docs"
                buildDir = "${rootProject.rootDir}/build/docs"
                publish.docPath = "_site"
            }
            val preBuildDocsTask =
                PrebuildSiteTask.register(project) {
                    projectsInfo.set(project.projectsInfo)
                    allTests.set(reports.allTests)
                    codeAnalysis.set(reports.codeAnalysis)
                    codeCoverage.set(reports.codeCoverage)
                    codeQuality.set(reports.codeQuality)
                }

            BuildSiteTask.register(project, preBuildDocsTask)
        }
    }
}

internal val HubdleEnableableExtension.hubdleSite: HubdleConfigDocumentationSiteExtension
    get() = getHubdleExtension()
