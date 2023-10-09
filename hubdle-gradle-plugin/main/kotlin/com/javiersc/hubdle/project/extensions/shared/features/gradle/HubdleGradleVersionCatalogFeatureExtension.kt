package com.javiersc.hubdle.project.extensions.shared.features.gradle

import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.publishing.maven.configurableMavenPublishing
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleGradleVersionCatalogFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJava, hubdleKotlinJvm)

    override val priority: Priority = Priority.P3

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            PluginId.GradleVersionCatalog,
        )

        configurableMavenPublishing(mavenPublicationName = "versionCatalog")
    }
}

internal val HubdleEnableableExtension.hubdleGradleVersionCatalog:
    HubdleGradleVersionCatalogFeatureExtension
    get() = getHubdleExtension()
