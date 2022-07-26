package com.javiersc.hubdle.extensions.config.language.settings

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder

public open class HubdleLanguageSettingsExtension @Inject constructor(objects: ObjectFactory) {

    @HubdleDslMarker
    public fun Project.experimentalContracts(enabled: Boolean = true) {
        hubdleState.config.languageSettings.experimentalContracts = enabled
    }

    @HubdleDslMarker
    public fun Project.experimentalCoroutinesApi(enabled: Boolean = true) {
        hubdleState.config.languageSettings.experimentalCoroutinesApi = enabled
    }

    @HubdleDslMarker
    public fun Project.experimentalSerializationApi(enabled: Boolean = true) {
        hubdleState.config.languageSettings.experimentalSerializationApi = enabled
    }

    @HubdleDslMarker
    public fun Project.experimentalStdlibApi(enabled: Boolean = true) {
        hubdleState.config.languageSettings.experimentalStdlibApi = enabled
    }

    @HubdleDslMarker
    public fun Project.experimentalTime(enabled: Boolean = true) {
        hubdleState.config.languageSettings.experimentalTime = enabled
    }

    @HubdleDslMarker
    public fun Project.flowPreview(enabled: Boolean = true) {
        hubdleState.config.languageSettings.flowPreview = enabled
    }

    @HubdleDslMarker
    public fun Project.ktorInternalAPI(enabled: Boolean = true) {
        hubdleState.config.languageSettings.ktorInternalAPI = enabled
    }

    @HubdleDslMarker
    public fun Project.optIn(annotationName: String) {
        hubdleState.config.languageSettings.optIns += annotationName
    }

    @HubdleDslMarker
    public fun Project.optIn(vararg annotationNames: String) {
        hubdleState.config.languageSettings.optIns += annotationNames
    }

    @HubdleDslMarker
    public fun Project.requiresOptIn(enabled: Boolean = true) {
        hubdleState.config.languageSettings.requiresOptIn = enabled
    }

    private val rawConfig: RawConfig = objects.newInstance()

    @HubdleDslMarker
    public fun rawConfig(action: Action<RawConfig> = Action {}) {
        action.execute(rawConfig)
    }

    public open class RawConfig {

        @HubdleDslMarker
        public fun Project.languageSettings(action: Action<LanguageSettingsBuilder> = Action {}) {
            hubdleState.config.languageSettings.rawConfig.languageSettings = action
        }
    }
}
