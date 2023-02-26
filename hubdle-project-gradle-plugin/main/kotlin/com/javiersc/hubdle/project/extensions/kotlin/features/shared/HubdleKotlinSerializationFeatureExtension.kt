package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.catalogDependency
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleKotlinSerializationFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val priority: Priority = Priority.P4

    public val json: Property<Boolean> = property { true }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinPluginSerialization
        )

        configurable {
            forKotlinSetsDependencies(MAIN, COMMON_MAIN) {
                implementation(
                    catalogDependency(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE)
                )
                if (json.get()) {
                    implementation(
                        catalogDependency(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE)
                    )
                }
            }
        }
    }
}

public interface HubdleKotlinSerializationDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val serialization: HubdleKotlinSerializationFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun serialization(
        action: Action<HubdleKotlinSerializationFeatureExtension> = Action {}
    ) {
        serialization.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleSerializationFeature:
    HubdleKotlinSerializationFeatureExtension
    get() = getHubdleExtension()
