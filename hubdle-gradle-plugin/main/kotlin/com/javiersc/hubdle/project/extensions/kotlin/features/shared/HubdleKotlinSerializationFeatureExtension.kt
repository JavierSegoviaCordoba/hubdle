package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlinx_serialization_core
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlinx_serialization_json
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.softwork_kotlinx_serialization_csv
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.softwork_kotlinx_serialization_flf
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleKotlinSerializationFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

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
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinPluginSerialization,
        )

        lazyConfigurable {
            forKotlinSetsDependencies(MAIN, COMMON_MAIN) {
                implementation(library(jetbrains_kotlinx_serialization_core))
                if (csv.get()) implementation(library(softwork_kotlinx_serialization_csv))
                if (flf.get()) implementation(library(softwork_kotlinx_serialization_flf))
                if (json.get()) implementation(library(jetbrains_kotlinx_serialization_json))
            }
        }
    }
}

public interface HubdleKotlinSerializationDelegateFeatureExtension : BaseHubdleExtension {

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
