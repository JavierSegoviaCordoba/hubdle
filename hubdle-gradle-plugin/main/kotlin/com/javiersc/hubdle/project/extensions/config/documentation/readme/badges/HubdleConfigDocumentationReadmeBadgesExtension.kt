package com.javiersc.hubdle.project.extensions.config.documentation.readme.badges

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.HubdleConfigExtension.ProjectConfig
import com.javiersc.hubdle.project.extensions.config.analysis.tools.hubdleSonar
import com.javiersc.hubdle.project.extensions.config.documentation.readme.hubdleReadme
import com.javiersc.hubdle.project.extensions.config.publishing.maven.hubdlePublishingMavenPom
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

@HubdleDslMarker
public open class HubdleConfigDocumentationReadmeBadgesExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleReadme)

    override val priority: Priority = Priority.P3

    public val mainProjectName: Property<String> = property {
        getStringProperty(ProjectConfig.mainProjectName).orElse(name).get()
    }

    public val kotlin: Property<Boolean> = property { true }
    public val mavenCentral: Property<Boolean> = property { true }
    public val snapshots: Property<Boolean> = property { true }
    public val build: Property<Boolean> = property { true }
    public val coverage: Property<Boolean> = property { true }
    public val quality: Property<Boolean> = property { true }
    public val techDebt: Property<Boolean> = property { true }

    override fun Project.defaultConfiguration() {
        configurable {
            tasks.register<WriteReadmeBadgesTask>(WriteReadmeBadgesTask.NAME).configure { task ->
                task.projectGroup.set(group.toString())
                task.projectName.set(mainProjectName)
                task.repoUrl.set(hubdlePublishingMavenPom.scmUrl)
                task.kotlinVersion.set(getKotlinPluginVersion())
                task.kotlinBadge.set(kotlin)
                task.mavenCentralBadge.set(mavenCentral)
                task.snapshotsBadge.set(snapshots)
                task.buildBadge.set(build)
                task.coverageBadge.set(coverage)
                task.qualityBadge.set(quality)
                task.techDebtBadge.set(techDebt)
                task.projectKey.set(hubdleSonar.projectKey)
            }
        }
    }
}
