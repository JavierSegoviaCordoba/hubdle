package com.javiersc.hubdle.extensions.config.documentation.api

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions._internal.hasKotlinGradlePlugin
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.documentation.hubdleDocumentation
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaTaskPartial

@HubdleDslMarker
public open class HubdleConfigDocumentationApiExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleDocumentation)

    override val priority: Priority = Priority.P3

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsDokka
        )

        configurable {
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
