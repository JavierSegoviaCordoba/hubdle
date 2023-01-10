package com.javiersc.hubdle.extensions.apis

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.kotlin.stdlib.capitalize
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project

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

    internal fun calculateName(name: String): String =
        if (targetName != null) "${targetName}${name.capitalize()}" else name
}
