package com.javiersc.hubdle.project.extensions.config.testing

import com.gradle.enterprise.gradleplugin.testretry.TestRetryExtension
import com.gradle.enterprise.gradleplugin.testretry.retry
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
import com.javiersc.hubdle.project.extensions.kotlin.android.hubdleAndroidAny
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestReport
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

@HubdleDslMarker
public abstract class HubdleConfigTestingExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    override val priority: Priority = Priority.P3

    public val options: Property<Options> = property {
        if (hasAndroid.get()) Options.JUnit else Options.JUnitPlatform
    }

    public val maxParallelForks: Property<Int> = property {
        (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }

    @HubdleDslMarker
    public fun maxParallelForks(forks: Int) {
        maxParallelForks.set(forks)
    }

    @HubdleDslMarker
    public fun retry(action: Action<TestRetryExtension>) {
        userConfigurable {
            tasks.withType<Test>().configureEach { test -> action.execute(test.retry) }
        }
    }

    public val showStandardStreams: Property<Boolean> = property { true }

    @HubdleDslMarker
    public fun showStandardStreams(enabled: Boolean = true) {
        showStandardStreams.set(enabled)
    }

    @HubdleDslMarker
    public fun test(action: Action<Test>) {
        userConfigurable {
            project.tasks.withType<Test>().configureEach { test -> action.execute(test) }
        }
    }

    override fun Project.defaultConfiguration() {
        pluginManager.apply(BasePlugin::class)

        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.AdarshrTestLogger
        )

        configurable {
            val testReport: TaskProvider<TestReport> =
                tasks.register<TestReport>(ALL_TEST_REPORT_TASK_NAME)

            val shouldRunAllTestsReport: Boolean =
                gradle.startParameter.taskNames.any { taskName ->
                    taskName in listOf(ALL_TEST_TASK_NAME, BUILD_TASK_NAME, CHECK_TASK_NAME)
                }
            tasks.withType<Test>().configureEach { task ->
                task.testLogging.showStandardStreams = showStandardStreams.get()
                task.maxParallelForks = maxParallelForks.get()

                when (options.get()) {
                    Options.JUnit -> task.useJUnit()
                    Options.JUnitPlatform -> task.useJUnitPlatform()
                    Options.TestNG -> task.useTestNG()
                    else -> task.useJUnit()
                }

                if (shouldRunAllTestsReport) task.finalizedBy(testReport)
            }

            tasks.named(CHECK_TASK_NAME) { task -> task.dependsOn(ALL_TEST_TASK_NAME) }

            testReport.configure { task ->
                task.group = VERIFICATION_GROUP
                task.destinationDirectory.set(file("$buildDir/reports/allTests"))
                // TODO: Remove allprojects as it is incompatible with project isolation
                task.testResults.from(allprojects.map { it.tasks.withType<Test>() })
            }
        }

        configurable(priority = Priority.P6) {
            tasks.maybeRegisterLazily<TestReport>(ALL_TEST_TASK_NAME) { task ->
                task.group = VERIFICATION_GROUP
            }
            tasks.namedLazily<Task>(ALL_TEST_TASK_NAME).configureEach { task ->
                task.dependsOn(tasks.withType<Test>())
            }
        }
    }

    public enum class Options {
        JUnit,
        JUnitPlatform,
        TestNG,
    }
}

private val HubdleConfigTestingExtension.hasAndroid: Property<Boolean>
    get() = property {
        hubdleAndroidAny.any { it.isFullEnabled.get() } ||
            hubdleKotlinMultiplatform.isFullEnabled.get()
    }

internal const val ALL_TEST_TASK_NAME = "allTests"
internal const val ALL_TEST_REPORT_TASK_NAME = "allTestsReport"

internal val HubdleEnableableExtension.hubdleTesting: HubdleConfigTestingExtension
    get() = getHubdleExtension()

internal val Project.hubdleTesting: HubdleConfigTestingExtension
    get() = getHubdleExtension()
