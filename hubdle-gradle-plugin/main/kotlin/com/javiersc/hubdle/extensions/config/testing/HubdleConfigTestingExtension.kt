package com.javiersc.hubdle.extensions.config.testing

import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.hubdleConfig
import com.javiersc.hubdle.extensions.kotlin.android.hubdleAndroidAny
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestReport
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

    public val showStandardStreams: Property<Boolean> = property { true }

    public val options: Property<Options> = property {
        if (hasAndroid.get()) Options.JUnit else Options.JUnitPlatform
    }

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
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.AdarshrTestLogger
        )

        configurable {
            tasks.withType<Test>().configureEach { test ->
                test.testLogging.showStandardStreams = showStandardStreams.get()

                when (options.get()) {
                    Options.JUnit -> test.useJUnit()
                    Options.JUnitPlatform -> test.useJUnitPlatform()
                    Options.TestNG -> test.useTestNG()
                    else -> test.useJUnitPlatform()
                }
            }

            tasks.configureEach { task ->
                if (task.name == CHECK_TASK_NAME) task.dependsOn(ALL_TEST_TASK_NAME)
            }

            val testReport = tasks.maybeRegisterLazily<TestReport>(ALL_TEST_REPORT_TASK_NAME)
            testReport.configureEach { task ->
                task.group = VERIFICATION_GROUP
                task.destinationDirectory.set(file("$buildDir/reports/allTests"))
                task.testResults.from(allprojects.map { it.tasks.withType<Test>() })
            }

            val shouldRunAllTestsReport =
                gradle.startParameter.taskNames.any { taskName ->
                    taskName in listOf(ALL_TEST_TASK_NAME, BUILD_TASK_NAME, CHECK_TASK_NAME)
                }

            if (shouldRunAllTestsReport) {
                allprojects {
                    it.project.tasks.withType<Test>().configureEach { task ->
                        task.finalizedBy(testReport)
                    }
                }
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
