package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project

public open class KotlinMultiplatformAndroidExtension : KotlinMultiplatformTargetOptions {

    public override val name: String = "android"

    public fun Project.publishLibraryVariants(vararg names: String) {
        hubdleState.kotlin.multiplatform.android.publishLibraryVariants += names
    }

    public fun Project.publishAllLibraryVariants() {
        hubdleState.kotlin.multiplatform.android.allLibraryVariants = true
    }
}
