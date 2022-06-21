package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.kotlin.multiplatform.kotlinMultiplatformExtension
import org.gradle.api.Project

public open class KotlinMultiplatformAndroidExtension : KotlinMultiplatformTargetOptions {

    public override val name: String = "android"

    public fun Project.publishLibraryVariants(vararg names: String) {
        kotlinMultiplatformExtension.android { publishLibraryVariants(*names) }
    }

    public fun Project.publishAllLibraryVariants() {
        kotlinMultiplatformExtension.android { publishAllLibraryVariants() }
    }
}
