package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Action
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsNodeDsl

@HubdleDslMarker
public open class KotlinMultiplatformJsExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.js.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.js.run { isEnabled = value }

    public override val name: String = "js"

    @HubdleDslMarker
    public fun Project.browser(action: Action<KotlinJsBrowserDsl> = Action {}) {
        hubdleState.kotlin.multiplatform.js.browser.isEnabled = true
        hubdleState.kotlin.multiplatform.js.browser.action = action
    }

    @HubdleDslMarker
    public fun Project.nodejs(action: Action<KotlinJsNodeDsl> = Action {}) {
        hubdleState.kotlin.multiplatform.js.nodejs.isEnabled = true
        hubdleState.kotlin.multiplatform.js.nodejs.action = action
    }

    public companion object {
        internal const val BROWSER = false
        internal const val NODE_JS = false
    }
}
