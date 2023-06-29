package com.javiersc.hubdle.project.extensions._internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal val Project.kotlinSourceSetMain: KotlinSourceSet?
    get() =
        extensions.findByType<KotlinProjectExtension>()?.sourceSets?.firstOrNull {
            it.name == "main"
        }

internal val Project.kotlinSourceSetCommonMain: KotlinSourceSet?
    get() =
        extensions.findByType<KotlinProjectExtension>()?.sourceSets?.firstOrNull {
            it.name == "commonMain"
        }

internal val Project.kotlinSourceSetMainOrCommonMain: KotlinSourceSet?
    get() =
        extensions.findByType<KotlinProjectExtension>()?.sourceSets?.firstOrNull {
            it.name == "main" || it.name == "commonMain"
        }
