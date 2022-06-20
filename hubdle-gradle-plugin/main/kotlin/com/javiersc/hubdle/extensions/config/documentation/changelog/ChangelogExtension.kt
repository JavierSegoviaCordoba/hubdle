package com.javiersc.hubdle.extensions.config.documentation.changelog

import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.the
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

@HubdleDslMarker
public open class ChangelogExtension :
    EnableableOptions, RawConfigOptions<ChangelogExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    @HubdleDslMarker
    public fun Project.removeProjects(vararg paths: String) {
        tasks.namedLazily<DokkaMultiModuleTask>("dokkaHtmlMultiModule").configureEach {
            it.removeChildTasks(paths.map(::project))
        }
    }

    @HubdleDslMarker
    public fun Project.removeProjects(vararg projects: Project) {
        tasks.namedLazily<DokkaMultiModuleTask>("dokkaHtmlMultiModule").configureEach {
            it.removeChildTasks(projects.toList())
        }
    }

    @HubdleDslMarker
    public fun Project.removeProjects(vararg projects: ProjectDependency) {
        tasks.namedLazily<DokkaMultiModuleTask>("dokkaHtmlMultiModule").configureEach {
            it.removeChildTasks(projects.map(ProjectDependency::getDependencyProject))
        }
    }

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { action.execute(the()) }
    }

    public open class RawConfigExtension {
        public fun Project.changelog(action: Action<ChangelogPluginExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
