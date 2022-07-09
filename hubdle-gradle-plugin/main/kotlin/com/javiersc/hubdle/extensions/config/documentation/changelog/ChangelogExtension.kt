package com.javiersc.hubdle.extensions.config.documentation.changelog

import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

@HubdleDslMarker
public open class ChangelogExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<ChangelogExtension.RawConfigExtension> {

    override var Project.isEnabled: Boolean
        get() = hubdleState.config.documentation.changelog.isEnabled
        set(value) = hubdleState.config.documentation.changelog.run { isEnabled = value }

    @HubdleDslMarker
    public fun Project.removeProjects(vararg paths: String) {
        tasks.namedLazily<DokkaMultiModuleTask>("dokkaHtmlMultiModule").configureEach {
            removeChildTasks(paths.map(::project))
        }
    }

    @HubdleDslMarker
    public fun Project.removeProjects(vararg projects: Project) {
        tasks.namedLazily<DokkaMultiModuleTask>("dokkaHtmlMultiModule").configureEach {
            removeChildTasks(projects.toList())
        }
    }

    @HubdleDslMarker
    public fun Project.removeProjects(vararg projects: ProjectDependency) {
        tasks.namedLazily<DokkaMultiModuleTask>("dokkaHtmlMultiModule").configureEach {
            removeChildTasks(projects.map(ProjectDependency::getDependencyProject))
        }
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.changelog(action: Action<ChangelogPluginExtension>) {
            hubdleState.config.documentation.changelog.rawConfig.changelog = action
        }
    }
}
