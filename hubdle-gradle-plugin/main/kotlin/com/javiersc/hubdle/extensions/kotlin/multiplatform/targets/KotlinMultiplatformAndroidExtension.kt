package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

@HubdleDslMarker
public open class KotlinMultiplatformAndroidExtension :
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

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { action.execute(the()) }
    }

    public open class RawConfigExtension {
        public val Project.android: LibraryExtension
            get() = the()
    }

    public companion object {
        internal const val IS_ENABLED = false
        internal const val ALL_LIBRARY_VARIANTS = true
    }
}
