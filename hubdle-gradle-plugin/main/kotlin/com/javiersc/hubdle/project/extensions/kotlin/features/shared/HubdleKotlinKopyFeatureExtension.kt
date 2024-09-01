package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.javiersc.kotlin.kopy.args.KopyFunctions
import com.javiersc.kotlin.kopy.args.KopyVisibility
import com.javiersc.kotlin.kopy.gradle.plugin.KopyExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleKotlinKopyFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    public val functions: Property<KopyFunctions> = property { KopyFunctions.All }

    @HubdleDslMarker
    public fun functions(functions: KopyFunctions = KopyFunctions.All) {
        this.functions.set(functions)
    }

    public val visibility: Property<KopyVisibility> = property { KopyVisibility.Auto }

    @HubdleDslMarker
    public fun visibility(visibility: KopyVisibility = KopyVisibility.Auto) {
        this.visibility.set(visibility)
    }

    @HubdleDslMarker
    public fun kopy(action: Action<KopyExtension> = Action {}): Unit = fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            scope = Scope.CurrentProject,
            pluginId = PluginId.JavierscKotlinKopyGradlePlugin,
        )
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsKotlinAtomicfu)

        withPlugin(PluginId.JavierscKotlinKopyGradlePlugin) {
            configure<KopyExtension> {
                functions.set(this@HubdleKotlinKopyFeatureExtension.functions)
                visibility.set(this@HubdleKotlinKopyFeatureExtension.visibility)
            }
        }
    }
}

public interface HubdleKotlinKopyDelegateFeatureExtension : BaseHubdleExtension {

    public val kopy: HubdleKotlinKopyFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun kopy(action: Action<HubdleKotlinKopyFeatureExtension> = Action {}) {
        kopy.enableAndExecute(action)
    }
}
