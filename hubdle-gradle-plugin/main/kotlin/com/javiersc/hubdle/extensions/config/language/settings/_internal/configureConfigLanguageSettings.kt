package com.javiersc.hubdle.extensions.config.language.settings._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config._internal.hasKotlinGradlePlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun configureConfigLanguageSettings(project: Project) {
    if (project.hasKotlinGradlePlugin) {
        with(project.hubdleState.config.languageSettings) {
            project.the<KotlinProjectExtension>().sourceSets.all {
                languageSettings {
                    if (experimentalCoroutinesApi)
                        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                    if (experimentalStdlibApi) optIn("kotlin.ExperimentalStdlibApi")
                    if (experimentalTime) optIn("kotlin.time.ExperimentalTime")
                    if (flowPreview) optIn("kotlinx.coroutines.FlowPreview")
                    if (requiresOptIn) optIn("kotlin.RequiresOptIn")
                }

                rawConfig.languageSettings?.execute(languageSettings)
            }
        }
    }
}
