package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformTvOSExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.tvos.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                tvos.isEnabled = value
                tvosArm64.isEnabled = value
                tvosSimulatorArm64.isEnabled = value
                tvosX64.isEnabled = value
                darwin.isEnabled = value
            }

    override val name: String = "tvos"
}
