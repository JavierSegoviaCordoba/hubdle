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
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.kotlinx.atomicfu.gradle.AtomicfuKotlinGradleSubplugin.AtomicfuKotlinGradleExtension

public open class HubdleKotlinAtomicfuFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    public val isJsIrTransformationEnabled: Property<Boolean> = property { false }

    public val isJvmIrTransformationEnabled: Property<Boolean> = property { false }

    public val isNativeIrTransformationEnabled: Property<Boolean> = property { false }

    @HubdleDslMarker
    public fun jsIrTransformation(enable: Boolean = true) {
        isJsIrTransformationEnabled.set(enable)
    }

    @HubdleDslMarker
    public fun jvmIrTransformation(enable: Boolean = true) {
        isJvmIrTransformationEnabled.set(enable)
    }

    @HubdleDslMarker
    public fun nativeIrTransformation(enable: Boolean = true) {
        isNativeIrTransformationEnabled.set(enable)
    }

    @HubdleDslMarker
    public fun atomicfu(action: Action<AtomicfuKotlinGradleExtension> = Action {}): Unit =
        fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.JetbrainsKotlinAtomicfu)
        lazyConfigurable {
            configure<AtomicfuKotlinGradleExtension> {
                isJsIrTransformationEnabled =
                    this@HubdleKotlinAtomicfuFeatureExtension.isJsIrTransformationEnabled.get()
                isJvmIrTransformationEnabled =
                    this@HubdleKotlinAtomicfuFeatureExtension.isJvmIrTransformationEnabled.get()
                isNativeIrTransformationEnabled =
                    this@HubdleKotlinAtomicfuFeatureExtension.isNativeIrTransformationEnabled.get()
            }
        }
    }
}

public interface HubdleKotlinAtomicfuDelegateFeatureExtension : BaseHubdleExtension {

    public val atomicfu: HubdleKotlinAtomicfuFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun atomicfu(action: Action<HubdleKotlinAtomicfuFeatureExtension> = Action {}) {
        atomicfu.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleAtomicfuFeature: HubdleKotlinAtomicfuFeatureExtension
    get() = getHubdleExtension()

internal val Project.hubdleAtomicfuFeature: HubdleKotlinAtomicfuFeatureExtension
    get() = getHubdleExtension()
