package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.mingw

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformTargetOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformMinGWX64Extension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.mingwX64.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.mingwX64.run { isEnabled = value }

    override val name: String = "mingwX64"
}
