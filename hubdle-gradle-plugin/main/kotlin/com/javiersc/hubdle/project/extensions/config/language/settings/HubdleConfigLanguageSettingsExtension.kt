package com.javiersc.hubdle.project.extensions.config.language.settings

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder

public open class HubdleConfigLanguageSettingsExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    @HubdleDslMarker
    public fun experimentalCompilerApi() {
        optIn("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
    }

    @HubdleDslMarker
    public fun experimentalComposeUiApi() {
        optIn("androidx.compose.ui.ExperimentalComposeUiApi")
    }

    @HubdleDslMarker
    public fun experimentalContracts() {
        optIn("kotlin.contracts.ExperimentalContracts")
    }

    @HubdleDslMarker
    public fun experimentalCoroutinesApi() {
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }

    @HubdleDslMarker
    public fun experimentalFoundationApi() {
        optIn("androidx.compose.foundation.ExperimentalFoundationApi")
    }

    @HubdleDslMarker
    public fun experimentalMaterial3Api() {
        optIn("androidx.compose.material3.ExperimentalMaterial3Api")
    }

    @HubdleDslMarker
    public fun experimentalMaterial3WindowSizeClassApi() {
        optIn("androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi")
    }

    @HubdleDslMarker
    public fun experimentalMetricApi() {
        optIn("androidx.benchmark.macro.ExperimentalMetricApi")
    }

    @HubdleDslMarker
    public fun experimentalLayoutApi() {
        optIn("androidx.compose.foundation.layout.ExperimentalLayoutApi")
    }

    @HubdleDslMarker
    public fun experimentalSerializationApi() {
        optIn("kotlinx.serialization.ExperimentalSerializationApi")
    }

    @HubdleDslMarker
    public fun experimentalStdlibApi() {
        optIn("kotlin.ExperimentalStdlibApi")
    }

    @HubdleDslMarker
    public fun experimentalTime() {
        optIn("kotlin.time.ExperimentalTime")
    }

    @HubdleDslMarker
    public fun flowPreview() {
        optIn("kotlinx.coroutines.FlowPreview")
    }

    @HubdleDslMarker
    public fun ktorInternalAPI() {
        optIn("io.ktor.util.InternalAPI")
    }

    @HubdleDslMarker
    public fun requiresOptIn() {
        optIn("kotlin.RequiresOptIn")
    }

    @HubdleDslMarker
    public fun optIn(annotationName: String) {
        configurable {
            configure<KotlinProjectExtension> {
                sourceSets.all { set -> set.languageSettings.optIn(annotationName) }
            }
        }
    }

    @HubdleDslMarker
    public fun optIn(vararg annotationNames: String) {
        for (annotationName in annotationNames) optIn(annotationName)
    }

    @HubdleDslMarker
    public fun languageSettings(action: Action<LanguageSettingsBuilder> = Action {}) {
        configurable {
            configure<KotlinProjectExtension> {
                sourceSets.all { set -> set.languageSettings(action) }
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleLanguageSettings: HubdleConfigLanguageSettingsExtension
    get() = hubdleConfig.languageSettings
