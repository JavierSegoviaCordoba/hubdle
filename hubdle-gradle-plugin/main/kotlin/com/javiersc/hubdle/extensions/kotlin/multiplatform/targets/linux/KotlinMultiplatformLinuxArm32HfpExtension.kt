package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.linux

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformTargetOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformLinuxArm32HfpExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.linuxArm32Hfp.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.linuxArm32Hfp.run { isEnabled = value }

    override val name: String = "linuxArm32Hfp"
}
