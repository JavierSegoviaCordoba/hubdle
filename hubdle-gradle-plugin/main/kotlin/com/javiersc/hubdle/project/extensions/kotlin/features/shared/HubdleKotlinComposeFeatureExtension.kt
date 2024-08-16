package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions.android._internal.configureAndroidCommonExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.androidx_activity_compose
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.androidx_compose_compiler
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.project.extensions.kotlin.android.features.hubdleAndroidBuildFeatures
import com.javiersc.hubdle.project.extensions.kotlin.android.features.hubdleAndroidFeatures
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.compose.ComposeExtension

public open class HubdleKotlinComposeFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    public val compiler: Property<String?> = property {
        library(androidx_compose_compiler).orNull?.toString()
    }

    @HubdleDslMarker
    public fun compiler(dependencyNotation: String) {
        compiler.set(dependencyNotation)
    }

    public val compilerVersion: Property<String?> = property {
        library(androidx_compose_compiler).orNull?.versionConstraint?.displayName
    }

    @HubdleDslMarker
    public fun compilerVersion(version: String) {
        compilerVersion.set(version)
    }

    @HubdleDslMarker
    public fun compose(action: Action<ComposeExtension> = Action {}): Unit = fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsCompose)
        applicablePlugin(
            scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsKotlinPluginCompose)

        lazyConfigurable {
            if (hubdleAndroidFeatures.isFullEnabled.get()) {
                forKotlinSetsDependencies(MAIN) {
                    implementation(library(androidx_activity_compose))
                }
                hubdleAndroidBuildFeatures.compose.set(true)
                configureAndroidCommonExtension { defaultConfig { buildFeatures.compose = true } }
            }
        }
    }
}

public interface HubdleKotlinComposeDelegateFeatureExtension : BaseHubdleExtension {

    public val compose: HubdleKotlinComposeFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun compose(action: Action<HubdleKotlinComposeFeatureExtension> = Action {}) {
        compose.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleComposeFeature: HubdleKotlinComposeFeatureExtension
    get() = getHubdleExtension()

internal val Project.hubdleComposeFeature: HubdleKotlinComposeFeatureExtension
    get() = getHubdleExtension()
