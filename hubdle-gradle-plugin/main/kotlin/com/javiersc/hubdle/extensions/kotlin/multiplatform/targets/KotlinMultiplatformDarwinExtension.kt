package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformDarwinExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.darwin.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                darwin.isEnabled = value
                ios.isEnabled = value
                macos.isEnabled = value
                tvos.isEnabled = value
                watchos.isEnabled = value
                native.isEnabled = value
            }

    override val name: String = "darwin"
}
