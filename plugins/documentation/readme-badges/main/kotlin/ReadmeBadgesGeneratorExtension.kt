package com.javiersc.gradle.plugins.readme.badges.generator

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.property

open class ReadmeBadgesGeneratorExtension(project: Project) {

    val kotlin: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val mavenCentral: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val snapshots: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val build: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val coverage: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val quality: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val techDebt: Property<Boolean> = project.objects.property<Boolean>().convention(true)

    companion object {
        const val name = "readmeBadges"

        internal fun createExtension(project: Project) =
            project.extensions.create<ReadmeBadgesGeneratorExtension>(name, project)
    }
}

internal val Project.readmeBadgesExtension: ReadmeBadgesGeneratorExtension
    get() =
        checkNotNull(extensions.findByType(ReadmeBadgesGeneratorExtension::class)) {
            "`readme-badges-generator` plugin is not correctly applied"
        }
