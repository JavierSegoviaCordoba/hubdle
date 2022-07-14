package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.android.AndroidSdkOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class KotlinMultiplatformAndroidExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    AndroidSdkOptions,
    EnableableOptions,
    KotlinMultiplatformTargetOptions,
    RawConfigOptions<KotlinMultiplatformAndroidExtension.RawConfigExtension> {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.android.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.run { android.isEnabled = value }

    public override val name: String = "android"

    public var Project.namespace: String?
        get() = hubdleState.kotlin.multiplatform.android.namespace
        set(value) = hubdleState.kotlin.multiplatform.run { android.namespace = value }

    @HubdleDslMarker
    public fun Project.publishLibraryVariants(vararg names: String) {
        hubdleState.kotlin.multiplatform.android.publishLibraryVariants += names
    }

    @HubdleDslMarker
    public fun Project.publishAllLibraryVariants(enable: Boolean = true) {
        hubdleState.kotlin.multiplatform.android.allLibraryVariants = enable
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.android(action: Action<LibraryExtension>) {
            hubdleState.kotlin.multiplatform.android.rawConfig.android = action
        }
    }
}
