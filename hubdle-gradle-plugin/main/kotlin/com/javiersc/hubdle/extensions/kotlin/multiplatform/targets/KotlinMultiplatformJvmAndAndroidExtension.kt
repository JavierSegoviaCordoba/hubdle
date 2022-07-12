package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinMultiplatformJvmAndAndroidExtension :
    EnableableOptions, KotlinMultiplatformTargetOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.multiplatform.jvmAndAndroid.isEnabled
        set(value) = hubdleState.kotlin.multiplatform.jvmAndAndroid.run { isEnabled = value }

    public override val name: String = "jvmAndAndroid"
}
