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

    kotlin {
        compilerLibrary { //
            ideaVersionsEnabled(false)
        }
    }
}

afterEvaluate {
    val kotlin = extensions.getByType<KotlinMultiplatformExtension>()
    for (sourceSet in kotlin.sourceSets.sortedBy { it.name }) {
        fun Set<File>.relative(): List<String> =
            map { it.relativeTo(projectDir).invariantSeparatorsPath }.sorted()
        val kotlinDirs = sourceSet.kotlin.srcDirs.relative()
        val resourcesDirs = sourceSet.resources.srcDirs.relative()
        println("SOURCE_SET ${sourceSet.name} kotlin=$kotlinDirs resources=$resourcesDirs")
    }
}
