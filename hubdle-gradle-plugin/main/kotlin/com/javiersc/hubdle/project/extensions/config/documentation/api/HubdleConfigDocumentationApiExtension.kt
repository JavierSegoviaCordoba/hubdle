package com.javiersc.hubdle.project.extensions.config.documentation.api

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.hasKotlinGradlePlugin
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.documentation.hubdleDocumentation
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaTaskPartial

@HubdleDslMarker
public open class HubdleConfigDocumentationApiExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleDocumentation)

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsDokka)

        lazyConfigurable {
            if (project.hasKotlinGradlePlugin) {
                tasks.withType<DokkaTaskPartial> {
                    dokkaSourceSets.configureEach { set ->
                        val includes: List<String> = buildList {
                            if (projectDir.resolve("MODULE.md").exists()) add("MODULE.md")
                            if (projectDir.resolve("README.md").exists()) add("README.md")
                        }

                        if (includes.isNotEmpty()) set.includes.from(includes)
                    }
                }
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleApi: HubdleConfigDocumentationApiExtension
    get() = getHubdleExtension()

internal val Project.hubdleApi: HubdleConfigDocumentationApiExtension
    get() = getHubdleExtension()
