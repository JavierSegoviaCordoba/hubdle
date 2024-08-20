package com.javiersc.hubdle.project.extensions.shared.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.shared.features.gradle.HubdleGradlePluginFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.gradle.HubdleGradleVersionCatalogFeatureExtension
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.initialization.dsl.VersionCatalogBuilder
import org.gradle.api.plugins.catalog.CatalogPluginExtension
import org.gradle.api.provider.Property

public open class HubdleGradleFeatureExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJava, hubdleKotlinJvm)

    public val plugin: HubdleGradlePluginFeatureExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleGradlePluginFeatureExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }

    private val versionCatalog: HubdleGradleVersionCatalogFeatureExtension
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

    override fun Project.defaultConfiguration() {}
}

public interface HubdleGradleDelegateFeatureExtension : BaseHubdleExtension {

    public val gradle: HubdleGradleFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun gradle(action: Action<HubdleGradleFeatureExtension> = Action {}) {
        gradle.enableAndExecute(action)
    }
}
