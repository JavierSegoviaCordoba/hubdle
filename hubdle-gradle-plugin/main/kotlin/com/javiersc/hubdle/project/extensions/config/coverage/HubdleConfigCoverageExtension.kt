package com.javiersc.hubdle.project.extensions.config.coverage

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.libraryVersion
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.config.testing.ALL_TEST_TASK_NAME
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_intellij_deps_intellijCoverageAgent
import javax.inject.Inject
import kotlinx.kover.api.CoverageEngineVariant
import kotlinx.kover.api.IntellijEngine
import kotlinx.kover.api.KoverProjectConfig
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.the

@HubdleDslMarker
public open class HubdleConfigCoverageExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    override val priority: Priority = Priority.P3

    public val engine: Property<CoverageEngineVariant> = property {
        val engineVersion: String = libraryVersion(jetbrains_intellij_deps_intellijCoverageAgent)!!
        IntellijEngine(engineVersion)
    }

    @HubdleDslMarker
    public fun engine(engine: CoverageEngineVariant) {
        this.engine.set(engine)
    }

    @HubdleDslMarker
    public fun kover(action: Action<KoverProjectConfig>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.AllProjects,
            pluginId = PluginId.JetbrainsKotlinxKover
        )

        configurable {
            // TODO: Remove this check and `allprojects` when updating to Kover 0.7.0
            check(isRootProject) {
                "Hubdle `coverage()` must be only configured in the root project"
            }
            allprojects { allproject ->
                val hubdleKoverEngine = this@HubdleConfigCoverageExtension.engine
                allproject.the<KoverProjectConfig>().engine.set(hubdleKoverEngine)

                val koverReportTask = allproject.tasks.namedLazily<Task>("koverReport")

                allproject.tasks.namedLazily<Task>(ALL_TEST_TASK_NAME).configureEach { task ->
                    task.dependsOn(koverReportTask)
                }
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleCoverage: HubdleConfigCoverageExtension
    get() = getHubdleExtension()
