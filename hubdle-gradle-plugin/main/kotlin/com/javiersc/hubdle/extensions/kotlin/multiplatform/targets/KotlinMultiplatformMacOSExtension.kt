package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableAllOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformMacOSExtension :
    EnableableOptions, EnableableAllOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.macos.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.run { macos.isEnabled = value }

    override var Project.allEnabled: Boolean
        get() =
            hubdleState.kotlin.multiplatform.macosArm64.isEnabled &&
                hubdleState.kotlin.multiplatform.macosX64.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                macosArm64.isEnabled = value
                macosX64.isEnabled = value
            }

    override val name: String = "macos"
}
