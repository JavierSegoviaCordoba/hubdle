package com.javiersc.hubdle.extensions.shared.features.gradle

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.publishing._internal.configurableMavenPublishing
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlin
import java.io.File
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.plugins.catalog.CatalogPluginExtension
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure

public open class HubdleGradleVersionCatalogFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    override val priority: Priority = Priority.P3

    public val catalogs: ConfigurableFileCollection = project.files(emptyList<File>())

    @HubdleDslMarker
    public fun catalogs(vararg files: File) {
        catalogs.setFrom(files)
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            PluginId.GradleVersionCatalog,
        )

        configurable {
            configure<CatalogPluginExtension> {
                versionCatalog { catalog -> catalog.from(catalogs) }
            }
        }

        configurableMavenPublishing(mavenPublicationName = "versionCatalog")
    }
}

internal val HubdleEnableableExtension.hubdleGradleVersionCatalog:
    HubdleGradleVersionCatalogFeatureExtension
    get() = getHubdleExtension()
