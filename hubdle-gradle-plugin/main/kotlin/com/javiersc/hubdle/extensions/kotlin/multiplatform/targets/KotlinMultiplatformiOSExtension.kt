package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformiOSExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.ios.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                darwin.isEnabled = value
                ios.isEnabled = value
                iosArm32.isEnabled = value
                iosArm64.isEnabled = value
                iosSimulatorArm64.isEnabled = value
                iosX64.isEnabled = value
            }

    override val name: String = "ios"
}
