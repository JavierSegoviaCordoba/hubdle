package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformMinGWExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.mingw.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                mingw.isEnabled = value
                mingwX64.isEnabled = value
                mingwX86.isEnabled = value
                native.isEnabled = value
            }

    override val name: String = "mingw"
}
