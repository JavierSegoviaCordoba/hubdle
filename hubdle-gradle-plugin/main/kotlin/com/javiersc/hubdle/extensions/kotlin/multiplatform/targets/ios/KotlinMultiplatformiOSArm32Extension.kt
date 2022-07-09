package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.ios

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformTargetOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformiOSArm32Extension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.iosArm32.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.iosArm32.run { isEnabled = value }

    override val name: String = "iosArm32"
}
