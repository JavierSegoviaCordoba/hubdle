package com.javiersc.hubdle.extensions.kotlin.android

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project

public interface AndroidSdkOptions {

    public var Project.compileSdk: Int
        get() = hubdleState.kotlin.android.compileSdk
        set(value) = hubdleState.kotlin.android.run { compileSdk = value }

    public var Project.minSdk: Int
        get() = hubdleState.kotlin.android.minSdk
        set(value) = hubdleState.kotlin.android.run { minSdk = value }
}
