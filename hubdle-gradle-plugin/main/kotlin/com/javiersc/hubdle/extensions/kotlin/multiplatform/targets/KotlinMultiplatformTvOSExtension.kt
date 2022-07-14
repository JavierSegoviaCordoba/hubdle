package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableAllOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformTvOSExtension :
    EnableableOptions, EnableableAllOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.tvos.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.run { tvos.isEnabled = value }

    override var Project.allEnabled: Boolean
        get() =
            hubdleState.kotlin.multiplatform.tvosArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.tvosSimulatorArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.tvosX64.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                tvosArm64.isEnabled = value
                tvosSimulatorArm64.isEnabled = value
                tvosX64.isEnabled = value
            }

    override val name: String = "tvos"
}
