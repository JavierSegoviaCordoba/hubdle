package com.javiersc.hubdle.project.extensions.config.analysis

import com.javiersc.gradle.properties.extensions.setProperty
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import java.io.File
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.TaskCollection
import org.gradle.kotlin.dsl.findByType
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

@HubdleDslMarker
public open class HubdleConfigAnalysisExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    public object Qodana {
        public const val projectKey: String = "analysis.qodana.projectKey"
    }

    override fun Project.defaultConfiguration() {
        configurable {
            val checkAnalysisTask: TaskCollection<Task> =
                tasks.maybeRegisterLazily<Task>("checkAnalysis")
            checkAnalysisTask.configureEach { task -> task.group = "verification" }
            tasks.namedLazily<Task>(CHECK_TASK_NAME).configureEach { task ->
                task.dependsOn(checkAnalysisTask)
            }
        }
    }
}

internal val Project.kotlinSrcDirs: SetProperty<String>
    get() =
        this.setProperty {
            extensions
                .findByType<KotlinProjectExtension>()
                ?.sourceSets
                ?.flatMap { kotlinSourceSet -> kotlinSourceSet.kotlin.srcDirs }
                ?.filterNot { file ->
                    val relativePath = file.relativeTo(projectDir)
                    val dirs = relativePath.path.split(File.separatorChar)
                    dirs.any { dir -> dir.endsWith("Test") || dir == "test" }
                }
                ?.filter { file -> file.exists() }
                ?.mapNotNull(File::getPath)
                .orEmpty()
                .toSet()
        }

internal val Project.kotlinTestsSrcDirs: SetProperty<String>
    get() =
        this.setProperty {
            extensions
                .findByType<KotlinProjectExtension>()
                ?.sourceSets
                ?.flatMap { kotlinSourceSet -> kotlinSourceSet.kotlin.srcDirs }
                ?.filter { file ->
                    val relativePath = file.relativeTo(projectDir)
                    val dirs = relativePath.path.split(File.separatorChar)
                    dirs.any { dir -> dir.endsWith("Test") || dir == "test" }
                }
                ?.filter { file -> file.exists() }
                ?.mapNotNull(File::getPath)
                .orEmpty()
                .toSet()
        }

internal val HubdleEnableableExtension.hubdleAnalysis: HubdleConfigAnalysisExtension
    get() = getHubdleExtension()
