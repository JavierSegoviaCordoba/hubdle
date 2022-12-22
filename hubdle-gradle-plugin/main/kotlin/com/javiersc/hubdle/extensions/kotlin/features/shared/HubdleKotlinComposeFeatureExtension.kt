package com.javiersc.hubdle.extensions.kotlin.features.shared

import androidxComposeCompiler
import com.android.build.api.dsl.CommonExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.catalogDependency
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ANDROIDX_ACTIVITY_ACTIVITY_COMPOSE_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.extensions.kotlin.android.features.hubdleAndroidFeatures
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.ComposeExtension

public open class HubdleKotlinComposeFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val priority: Priority = Priority.P4

    public val compilerVersion: Property<String?> = property {
        androidxComposeCompiler().get().versionConstraint.displayName
    }

    @HubdleDslMarker
    public fun compilerVersion(version: String) {
        compilerVersion.set(version)
    }

    @HubdleDslMarker
    public fun compose(action: Action<ComposeExtension> = Action {}) {
        userConfigurable { action.invoke(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsCompose
        )
        configurable {
            configure<ComposeExtension> { kotlinCompilerPlugin.set(compilerVersion) }
            if (hubdleAndroidFeatures.isFullEnabled.get()) {
                forKotlinSetsDependencies("main") {
                    implementation(catalogDependency(ANDROIDX_ACTIVITY_ACTIVITY_COMPOSE_MODULE))
                }
                configure<CommonExtension<*, *, *, *>> {
                    defaultConfig {
                        buildFeatures.compose = true
                        composeOptions.kotlinCompilerExtensionVersion = compilerVersion.get()
                    }
                }
            }
        }
    }
}

public interface HubdleKotlinComposeDelegateFeatureExtension : BaseHubdleDelegateExtension {

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
