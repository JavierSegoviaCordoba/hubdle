package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformWatchOSExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.watchos.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                watchos.isEnabled = value
                watchosArm32.isEnabled = value
                watchosArm64.isEnabled = value
                watchosSimulatorArm64.isEnabled = value
                watchosX64.isEnabled = value
                watchosX86.isEnabled = value
                darwin.isEnabled = value
            }

    override val name: String = "watchos"
}
