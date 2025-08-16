package com.javiersc.hubdle.project.extensions.config.format

import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.libraryVersion
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.facebook_ktfmt
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlin
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.javiersc.hubdle.project.tasks.lifecycle.FixChecksTask
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

@HubdleDslMarker
public open class HubdleConfigFormatExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    public val includes: SetProperty<String> = setProperty {
        includedKotlinSourceSetDirsKotlinFiles.get()
    }

    @HubdleDslMarker
    public fun includes(vararg includes: String) {
        this.includes.addAll(includes.toList())
    }

    public val excludes: SetProperty<String> = setProperty {
        excludedSpecialDirs.get() + excludedResourceSourceSetDirsKotlinFiles.get()
    }

    @HubdleDslMarker
    public fun excludes(vararg excludes: String) {
        this.excludes.addAll(excludes.toList())
    }

    public val ktfmtVersion: Property<String?> = property { libraryVersion(facebook_ktfmt) }

    @HubdleDslMarker
    public fun ktfmtVersion(version: String) {
        ktfmtVersion.set(version)
    }

    @HubdleDslMarker
    public fun spotless(action: Action<SpotlessExtension>): Unit = fallbackAction(action)

    @HubdleDslMarker
    public fun spotlessPredeclare(action: Action<SpotlessExtensionPredeclare>): Unit =
        fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.DiffplugSpotless)

        lazyConfigurable {
            val checkFormat: TaskProvider<Task> = tasks.register("checkFormat")
            checkFormat.configure { task ->
                task.group = "verification"
                task.dependsOn("spotlessCheck")
            }

            tasks.named(CHECK_TASK_NAME).dependsOn(checkFormat)

            val applyFormat: TaskProvider<Task> = tasks.register("applyFormat")
            applyFormat.configure { task ->
                task.group = "verification"
                task.dependsOn("spotlessApply")
            }
            tasks.named(FixChecksTask.NAME).dependsOn(applyFormat)

            // if (isRootProject) {
            //     configure<SpotlessExtension> { predeclareDepsFromBuildscript() }
            // }

            if (hubdleKotlin.isFullEnabled.get()) {
                val checkKotlinFormat: TaskProvider<Task> = tasks.register("checkKotlinFormat")
                checkKotlinFormat.configure { task ->
                    task.group = "verification"
                    task.dependsOn("spotlessKotlinCheck")
                }

                checkFormat.dependsOn(checkKotlinFormat)

                val applyKotlinFormat: TaskProvider<Task> = tasks.register("applyKotlinFormat")
                applyKotlinFormat.configure { task ->
                    task.group = "verification"
                    task.dependsOn("spotlessKotlinApply")
                }

                applyFormat.dependsOn(applyKotlinFormat)
            }
        }

        lazyConfigurable {
            // if (isRootProject) {
            //     configure<SpotlessExtensionPredeclare> {
            //         kotlin { kotlin -> kotlin.ktfmt(ktfmtVersion.get()) }
            //     }
            // }

            configure<SpotlessExtension> {
                kotlin { kotlin ->
                    kotlin.target(includes.get())
                    kotlin.targetExclude(excludes.get())
                    kotlin.ktfmt(ktfmtVersion.get()).kotlinlangStyle()
                }
            }
        }
    }

    private val includedKotlinSourceSetDirsKotlinFiles: SetProperty<String>
        get() = setProperty {
            extensions
                .findByType<KotlinProjectExtension>()
                ?.sourceSets
                ?.asSequence()
                ?.flatMap { it.kotlin.sourceDirectories }
                ?.map { it.relativeTo(projectDir) }
                ?.filterNot { it.path.startsWith(layout.buildDirectory.asFile.get().name) }
                ?.flatMap { listOf("${it.unixPath}/**/*.kt", "${it.unixPath}/*.kt") }
                ?.toSet()
                .orEmpty()
        }

    private val excludedSpecialDirs: SetProperty<String>
        get() = setProperty {
            setOf(
                "${layout.buildDirectory.asFile.get().name}/**/*.kt",
                "${layout.buildDirectory.asFile.get().name}/*.kt",
                ".gradle/**/*.kt",
                ".gradle/*.kt",
            )
        }

    private val excludedResourceSourceSetDirsKotlinFiles: SetProperty<String>
        get() = setProperty {
            extensions
                .findByType<KotlinProjectExtension>()
                ?.sourceSets
                ?.asSequence()
                ?.flatMap { it.resources.sourceDirectories }
                ?.map { it.relativeTo(projectDir) }
                ?.flatMap { listOf("${it.unixPath}/**/*.kt", "${it.unixPath}/*.kt") }
                ?.toSet()
                .orEmpty()
        }

    private val File.unixPath: String
        get() = path.replace("\\", "/")
}

internal val HubdleEnableableExtension.hubdleFormat: HubdleConfigFormatExtension
    get() = getHubdleExtension()
