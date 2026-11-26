@file:Suppress("PackageDirectoryMismatch")

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins { //
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        versioning { //
            isEnabled.set(false)
        }
    }

    kotlin { //
        compilerLibrary()
    }
}

afterEvaluate {
    val kotlin = extensions.getByType<KotlinMultiplatformExtension>()
    for (target in kotlin.targets.sortedBy { it.name }) println("TARGET ${target.name}")
}
