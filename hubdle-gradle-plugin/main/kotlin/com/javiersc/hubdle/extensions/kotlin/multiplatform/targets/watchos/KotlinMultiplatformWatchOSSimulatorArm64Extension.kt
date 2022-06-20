package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.watchos

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformTargetOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions

@HubdleDslMarker
public open class KotlinMultiplatformWatchOSSimulatorArm64Extension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var isEnabled: Boolean = IS_ENABLED

    override val name: String = "watchosSimulatorArm64"

    public companion object {
        internal const val IS_ENABLED = false
    }
}
