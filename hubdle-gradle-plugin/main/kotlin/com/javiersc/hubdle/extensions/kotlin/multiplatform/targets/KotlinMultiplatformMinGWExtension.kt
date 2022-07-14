package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableAllOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformMinGWExtension :
    EnableableOptions, EnableableAllOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.mingw.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.run { mingw.isEnabled = value }

    override var Project.allEnabled: Boolean
        get() =
            hubdleState.kotlin.multiplatform.mingwX64.isEnabled &&
                hubdleState.kotlin.multiplatform.mingwX86.isEnabled
        set(value) =
            hubdleState.kotlin.multiplatform.run {
                mingw.isEnabled = value
                mingwX64.isEnabled = value
                mingwX86.isEnabled = value
            }

    override val name: String = "mingw"
}
