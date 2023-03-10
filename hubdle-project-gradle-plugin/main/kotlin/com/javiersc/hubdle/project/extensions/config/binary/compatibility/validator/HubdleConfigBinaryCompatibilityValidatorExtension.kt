package com.javiersc.hubdle.project.extensions.config.binary.compatibility.validator

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import javax.inject.Inject
import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property

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
                val apiCheckTask = allproject.tasks.namedLazily<Task>("apiCheck")
                val checkApiTask = allproject.tasks.maybeRegisterLazily<Task>("checkApi")
                checkApiTask.configureEach { task ->
                    task.group = "verification"
                    task.dependsOn(apiCheckTask)
                }

                val apiDumpTask = allproject.tasks.namedLazily<Task>("apiDump")
                val dumpApiTask = allproject.tasks.maybeRegisterLazily<Task>("dumpApi")
                dumpApiTask.configureEach { task -> task.dependsOn(apiDumpTask) }
            }
        }
    }
}

internal val HubdleEnableableExtension.hubdleBinaryCompatibilityValidator:
    HubdleConfigBinaryCompatibilityValidatorExtension
    get() = getHubdleExtension()

private val Project.apiValidationExtension: ApiValidationExtension
    get() = getHubdleExtension()
