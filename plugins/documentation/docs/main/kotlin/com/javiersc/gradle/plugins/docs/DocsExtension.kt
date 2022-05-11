package com.javiersc.gradle.plugins.docs

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.property

open class DocsExtension(project: Project) {

    fun navigation(action: Action<in NavigationOptions>) = action.execute(navigation.get())

    val navigation: Property<NavigationOptions> =
        project.objects
            .property<NavigationOptions>()
            .value(project.objects.newInstance(NavigationOptions::class.java))

    companion object {
        const val name: String = "docs"

        internal fun createExtension(project: Project) =
            project.extensions.create<DocsExtension>(name, project)
    }
}

internal val Project.docsExtension: DocsExtension
    get() =
        checkNotNull(extensions.findByType(DocsExtension::class)) {
            "`docs` plugin is not correctly applied"
        }
