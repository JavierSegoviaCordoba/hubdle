package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.mingw

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformTargetOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions

@HubdleDslMarker
public open class KotlinMultiplatformMinGWX86Extension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var isEnabled: Boolean = IS_ENABLED

    override val name: String = "mingwX86"

    public companion object {
        internal const val IS_ENABLED = false
    }
}
