package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableAllOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformWAsmExtension :
    EnableableOptions, EnableableAllOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.wasm.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.run { wasm.isEnabled = value }

    override var Project.allEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.wasm32.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                wasm.isEnabled = value
                wasm32.isEnabled = value
            }

    override val name: String = "wasm"
}
