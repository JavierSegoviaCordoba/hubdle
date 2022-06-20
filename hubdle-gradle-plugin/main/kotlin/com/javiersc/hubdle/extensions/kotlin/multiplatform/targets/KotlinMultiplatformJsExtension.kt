package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformJsExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var isEnabled: Boolean = IS_ENABLED

    public override val name: String = "js"

    @HubdleDslMarker
    public fun Project.browser() {
        hubdleState.kotlin.multiplatform.js.browser = true
    }

    @HubdleDslMarker
    public fun Project.nodeJs() {
        hubdleState.kotlin.multiplatform.js.nodeJs = true
    }

    public companion object {
        internal const val IS_ENABLED = false
        internal const val BROWSER = false
        internal const val NODE_JS = false
    }
}
