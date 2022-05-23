package com.javiersc.gradle.plugins.all.projects

import com.javiersc.gradle.plugins.all.projects.install.InstallOptions
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.property

open class AllProjectsExtension(project: Project) {

    val install: Property<InstallOptions> =
        project.objects
            .property<InstallOptions>()
            .convention(project.objects.newInstance<InstallOptions>())

    fun install(action: Action<in InstallOptions>) = action.execute(install.get())

    companion object {
        const val name: String = "allProjectsConfig"

        internal fun createExtension(project: Project) =
            project.extensions.create<AllProjectsExtension>(name, project)
    }
}

internal val Project.allProjectsExtension: AllProjectsExtension
    get() =
        checkNotNull(extensions.findByType()) { "`all-projects` plugin is not correctly applied" }
