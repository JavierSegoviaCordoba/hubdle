package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformLinuxExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.linux.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                linux.isEnabled = value
                linuxArm32Hfp.isEnabled = value
                linuxArm64.isEnabled = value
                linuxMips32.isEnabled = value
                linuxMipsel32.isEnabled = value
                linuxX64.isEnabled = value
                native.isEnabled = value
            }

    override val name: String = "linux"
}
