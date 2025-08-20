package com.javiersc.hubdle.project.extensions.config.documentation.site

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.documentation.hubdleDocumentation
import com.javiersc.hubdle.project.extensions.config.documentation.site.analysis.HubdleConfigDocumentationSiteAnalysisExtension
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

@HubdleDslMarker
public open class HubdleConfigDocumentationSiteExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleDocumentation)

    public val analysis: HubdleConfigDocumentationSiteAnalysisExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun analysis(action: Action<HubdleConfigDocumentationSiteAnalysisExtension>) {
        analysis.enableAndExecute(action)
    }

    @HubdleDslMarker
    public fun mkdocs(action: Action<MkdocsExtension>): Unit = fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.VyarusMkdocsBuild)
        lazyConfigurable {
            check(isRootProject) {
                """
                |`site` can be only used in the root project, and it is being applied in:
                |  - `${displayName}`"""
                    .trimMargin()
            }
            configure<MkdocsExtension> {
                strict = false
                sourcesDir = "${rootProject.rootDir}/build/.docs"
                buildDir = "${rootProject.rootDir}/build/docs"
                publish.docPath = "_site"
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleSite: HubdleConfigDocumentationSiteExtension
    get() = getHubdleExtension()

internal val Project.hubdleSite: HubdleConfigDocumentationSiteExtension
    get() = getHubdleExtension()
