package com.javiersc.hubdle.project.extensions.config.binary.compatibility.validator

import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.tasks.extensions.maybeNamed
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.tasks.lifecycle.FixChecksTask
import javax.inject.Inject
import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskProvider

@HubdleDslMarker
public open class HubdleConfigBinaryCompatibilityValidatorExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    override val priority: Priority = Priority.P3

    @HubdleDslMarker
    public fun apiValidation(action: Action<ApiValidationExtension>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinxBinaryCompatibilityValidator
        )

        configurable {
            // TODO: Change this configurable with 0.7.0 as it must be applied on each project
            check(isRootProject) {
                "`binaryCompatibilityValidator` must be applied only on root project"
            }
            allprojects { allproject ->
                val checkApiTask: TaskProvider<Task> = allproject.tasks.register("checkApi")
                val dumpApiTask: TaskProvider<Task> = allproject.tasks.register("dumpApi")
                tasks.named(FixChecksTask.NAME).dependsOn(dumpApiTask)

                allproject.afterEvaluate {
                    val apiCheckTask: TaskProvider<Task>? = allproject.tasks.maybeNamed("apiCheck")
                    checkApiTask.configure { task ->
                        task.group = "verification"
                        apiCheckTask?.let { task.dependsOn(it) }
                    }

                    val apiDumpTask: TaskProvider<Task>? = allproject.tasks.maybeNamed("apiDump")
                    dumpApiTask.configure { task ->
                        task.group = "verification"
                        apiDumpTask?.let { task.dependsOn(it) }
                    }
                }
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleBinaryCompatibilityValidator:
    HubdleConfigBinaryCompatibilityValidatorExtension
    get() = getHubdleExtension()
