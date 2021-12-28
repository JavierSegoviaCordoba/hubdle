package com.javiersc.gradle.plugins.kotlin.config

import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun KotlinSourceSet.addDefaultLanguageSettings() {
    languageSettings {
        optIn("kotlin.ExperimentalStdlibApi")
        optIn("kotlin.RequiresOptIn")
        optIn("kotlin.time.ExperimentalTime")
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        optIn("kotlinx.coroutines.FlowPreview")
        optIn("kotlinx.coroutines.ObsoleteCoroutinesApi")
        optIn("kotlinx.serialization.ExperimentalSerializationApi")
        optIn("kotlinx.serialization.InternalSerializationApi")
        optIn("io.ktor.util.KtorExperimentalAPI")
    }
}
