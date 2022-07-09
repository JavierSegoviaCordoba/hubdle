package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.macos

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformTargetOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformMacOSX64Extension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.macosX64.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.macosX64.run { isEnabled = value }

    override val name: String = "macosX64"
}
