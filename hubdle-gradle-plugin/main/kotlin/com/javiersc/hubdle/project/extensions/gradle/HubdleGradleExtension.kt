package com.javiersc.hubdle.project.extensions.gradle

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.initialization.dsl.VersionCatalogBuilder
import org.gradle.api.plugins.catalog.CatalogPluginExtension
import org.gradle.api.provider.Property

public open class HubdleGradleExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = emptySet()

    public val plugin: HubdleGradlePluginExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleGradlePluginExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }

    private val versionCatalog: HubdleGradleVersionCatalogExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun versionCatalogs(action: Action<CatalogPluginExtension> = Action {}) {
        versionCatalog.enabled(true)
        lazyConfigurable { action.execute(the()) }
    }

    @HubdleDslMarker
    public fun CatalogPluginExtension.catalog(action: Action<VersionCatalogBuilder> = Action {}) {
        this.versionCatalog(action::execute)
    }

    @HubdleDslMarker
    public fun VersionCatalogBuilder.toml(toml: File) {
        from(project.files(toml))
    }

    @HubdleDslMarker
    public fun VersionCatalogBuilder.toml(toml: RegularFile) {
        from(project.files(toml))
    }

    override fun Project.defaultConfiguration() {}
}
