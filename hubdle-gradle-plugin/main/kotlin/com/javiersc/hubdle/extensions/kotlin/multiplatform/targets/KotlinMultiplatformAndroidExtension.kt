package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
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
    EnableableOptions,
    KotlinMultiplatformTargetOptions,
    RawConfigOptions<KotlinMultiplatformAndroidExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    public override val name: String = "android"

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

        @HubdleDslMarker
        public fun Project.android(action: Action<LibraryExtension>) {
            hubdleState.kotlin.multiplatform.android.rawConfig.android = action
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
        internal const val ALL_LIBRARY_VARIANTS = true
    }
}
