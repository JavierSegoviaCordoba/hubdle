package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.options.EnableableOptions

@HubdleDslMarker
public open class KotlinMultiplatformNativeExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var isEnabled: Boolean = IS_ENABLED

    override val name: String = "native"

    public companion object {
        internal const val IS_ENABLED = false
    }
}
