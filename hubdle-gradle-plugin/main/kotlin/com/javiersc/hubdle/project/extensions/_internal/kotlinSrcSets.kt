package com.javiersc.hubdle.project.extensions._internal

import org.gradle.api.NamedDomainObjectSet
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal val Project.kotlinSourceSetMainOrCommonMain: NamedDomainObjectSet<KotlinSourceSet>
    get() =
        the<KotlinProjectExtension>().sourceSets.named { name ->
            name == "main" || name == "commonMain"
        }

internal val Project.kotlinSourceSetMainOrCommonMainOrNull: NamedDomainObjectSet<KotlinSourceSet>?
    get() =
        extensions.findByType<KotlinProjectExtension>()?.sourceSets?.named { name ->
            name == "main" || name == "commonMain"
        }

internal val Project.kotlinSourceSetTestOrCommonTest: NamedDomainObjectSet<KotlinSourceSet>
    get() =
        the<KotlinProjectExtension>().sourceSets.named { name ->
            name == "test" || name == "commonTest"
        }

internal val Project.kotlinSourceSetTestOrCommonTestOrNull: NamedDomainObjectSet<KotlinSourceSet>?
    get() =
        extensions.findByType<KotlinProjectExtension>()?.sourceSets?.named { name ->
            name == "test" || name == "commonTest"
        }
