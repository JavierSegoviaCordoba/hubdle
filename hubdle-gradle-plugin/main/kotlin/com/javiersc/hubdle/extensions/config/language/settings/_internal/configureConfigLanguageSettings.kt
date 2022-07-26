package com.javiersc.hubdle.extensions.config.language.settings._internal

import com.javiersc.hubdle.extensions._internal.state.HubdleState.Config.LanguageSettings
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config._internal.hasKotlinGradlePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder

internal fun configureConfigLanguageSettings(project: Project) {
    if (project.hasKotlinGradlePlugin) {
        val languageSettingsState = project.hubdleState.config.languageSettings
        project.the<KotlinProjectExtension>().sourceSets.all {
            languageSettings { configureOptIn(languageSettingsState) }

            languageSettingsState.rawConfig.languageSettings?.execute(languageSettings)
        }
    }
}

private fun LanguageSettingsBuilder.configureOptIn(languageSettingsState: LanguageSettings) {
    with(languageSettingsState) {
        for (optIn in optIns) {
            optIn(optIn)
        }

        if (experimentalContracts) optIn("kotlin.contracts.ExperimentalContracts")
        if (experimentalCoroutinesApi) optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        if (experimentalSerializationApi) {
            optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }
        if (experimentalStdlibApi) optIn("kotlin.ExperimentalStdlibApi")
        if (experimentalTime) optIn("kotlin.time.ExperimentalTime")
        if (flowPreview) optIn("kotlinx.coroutines.FlowPreview")
        if (ktorInternalAPI) optIn("io.ktor.util.InternalAPI")
        if (requiresOptIn) optIn("kotlin.RequiresOptIn")
    }
}
