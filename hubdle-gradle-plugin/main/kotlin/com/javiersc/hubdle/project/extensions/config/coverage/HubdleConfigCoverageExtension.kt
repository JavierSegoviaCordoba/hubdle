package com.javiersc.hubdle.project.extensions.config.coverage

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import com.javiersc.hubdle.project.tasks.lifecycle.TestsTask
import javax.inject.Inject
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import kotlinx.kover.gradle.plugin.dsl.KoverReportExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskProvider
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

    public val jacoco: Property<String?> = property { null }

    @HubdleDslMarker
    public fun jacoco(version: String?) {
        this.jacoco.set(version)
    }

    @HubdleDslMarker
    public fun kover(action: Action<KoverProjectExtension>) {
        userConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun koverReport(action: Action<KoverReportExtension>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JetbrainsKotlinxKover
        )

        configurable {
            val kover: KoverProjectExtension = project.the()
            val jacoco: Property<String?> = this@HubdleConfigCoverageExtension.jacoco
            val jacocoVersion: String? = jacoco.orNull
            if (jacocoVersion != null) kover.useJacoco(jacocoVersion)

            configure<KoverReportExtension> {
                val buildDir: DirectoryProperty = layout.buildDirectory
                defaults { defaults ->
                    defaults.html { html -> html.setReportDir(buildDir.dir("reports/kover/html/")) }
                    defaults.xml { xml ->
                        xml.setReportFile(buildDir.file("reports/kover/xml/report.xml"))
                    }
                }
            }

            val koverHtmlReportTask = tasks.named("koverHtmlReport")
            val koverXmlReportTask = tasks.named("koverXmlReport")

            val koverReportTask: TaskProvider<Task> =
                // TODO: replace with `maybeRegister("koverReport")`
                if (!tasks.names.contains("koverReport")) tasks.register("koverReport")
                else tasks.named("koverReport")

            koverReportTask.configure { task ->
                task.group = "verification"
                task.dependsOn(koverHtmlReportTask)
                task.dependsOn(koverXmlReportTask)
            }

            tasks.named(TestsTask.NAME).configure { task -> task.dependsOn(koverReportTask) }
        }
    }
}

internal val HubdleEnableableExtension.hubdleCoverage: HubdleConfigCoverageExtension
    get() = getHubdleExtension()
