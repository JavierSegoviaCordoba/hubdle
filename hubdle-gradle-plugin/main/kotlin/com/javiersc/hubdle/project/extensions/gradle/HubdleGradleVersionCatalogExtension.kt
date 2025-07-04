package com.javiersc.hubdle.project.extensions.gradle

import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.publishing.maven.configurableMavenPublishing
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.vanniktech.maven.publish.VersionCatalog
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleGradleVersionCatalogExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = emptySet()

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, PluginId.GradleVersionCatalog)

        configurableMavenPublishing { it.configure(VersionCatalog()) }
    }
}

internal val HubdleEnableableExtension.hubdleGradleVersionCatalog:
    HubdleGradleVersionCatalogExtension
    get() = getHubdleExtension()
