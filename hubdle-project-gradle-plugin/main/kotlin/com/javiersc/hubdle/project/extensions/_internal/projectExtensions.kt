package com.javiersc.hubdle.project.extensions._internal

import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.setProperty

// TODO: Extract to Gradle Extensions libraries
internal inline fun <reified T> Project.property(crossinline block: Project.() -> T): Property<T> =
    objects.property<T>().convention(project.provider { block(this) })

// TODO: Extract to Gradle Extensions libraries
internal inline fun <reified T> Project.listProperty(
    crossinline block: Project.() -> List<T>
): ListProperty<T> = objects.listProperty<T>().convention(project.provider { block(this) })

// TODO: Extract to Gradle Extensions libraries
internal inline fun <reified T> Project.setProperty(
    crossinline block: Project.() -> Set<T>
): SetProperty<T> = objects.setProperty<T>().convention(project.provider { block(this) })
