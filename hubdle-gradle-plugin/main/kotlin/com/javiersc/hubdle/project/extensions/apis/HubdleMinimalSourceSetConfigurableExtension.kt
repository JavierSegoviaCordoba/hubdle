package com.javiersc.hubdle.project.extensions.apis

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.kotlin.stdlib.capitalize
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public abstract class HubdleMinimalSourceSetConfigurableExtension<T>(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val project: Project
        get() = super.project

    internal open val targetName: String? = null

    public abstract val main: NamedDomainObjectProvider<T>

    @HubdleDslMarker
    public fun main(action: Action<T>) {
        userConfigurable { main.configure(action) }
    }

    public abstract val test: NamedDomainObjectProvider<T>

    @HubdleDslMarker
    public fun test(action: Action<T>) {
        userConfigurable { test.configure(action) }
    }

    @HubdleDslMarker
    public fun sourceSet(name: String, action: Action<KotlinSourceSet>) {
        userConfigurable {
            configure<KotlinProjectExtension> { sourceSets.named(name, action::execute) }
        }
    }

    internal fun calculateName(name: String): String =
        if (targetName != null) "${targetName}${name.capitalize()}" else name
}
