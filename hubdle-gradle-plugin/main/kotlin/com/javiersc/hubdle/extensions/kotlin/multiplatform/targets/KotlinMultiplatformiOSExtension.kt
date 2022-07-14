package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableAllOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformiOSExtension :
    EnableableOptions, EnableableAllOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.ios.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.run { ios.isEnabled = value }

    override var Project.allEnabled: Boolean
        get() =
            hubdleState.kotlin.multiplatform.iosArm32.isEnabled &&
                hubdleState.kotlin.multiplatform.iosArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.iosSimulatorArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.iosX64.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                iosArm32.isEnabled = value
                iosArm64.isEnabled = value
                iosSimulatorArm64.isEnabled = value
                iosX64.isEnabled = value
            }

    override val name: String = "ios"
}
