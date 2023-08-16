package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlinx_kotlinxSerializationCore
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlinx_kotlinxSerializationJson
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.softwork_kotlinxSerializationCsv
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.softwork_kotlinxSerializationFlf
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

    public val csv: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun csv(enabled: Boolean = true) {
        csv.set(enabled)
    }

    public val flf: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun flf(enabled: Boolean = true) {
        flf.set(enabled)
    }

    public val json: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun json(enabled: Boolean = true) {
        json.set(enabled)
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinPluginSerialization
        )

        configurable {
            forKotlinSetsDependencies(MAIN, COMMON_MAIN) {
                implementation(library(jetbrains_kotlinx_kotlinxSerializationCore))
                if (csv.get()) implementation(library(softwork_kotlinxSerializationCsv))
                if (flf.get()) implementation(library(softwork_kotlinxSerializationFlf))
                if (json.get()) implementation(library(jetbrains_kotlinx_kotlinxSerializationJson))
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
