package com.javiersc.hubdle.extensions.kotlin.gradle

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.KotlinGradlePluginExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog.KotlinGradleVersionCatalogExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class KotlinGradleExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val plugin: KotlinGradlePluginExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.plugin(action: Action<KotlinGradlePluginExtension> = Action {}) {
        pluginManager.apply(PluginIds.Gradle.kotlinDsl)
        plugin.run { isEnabled = true }
        action.execute(plugin)
    }

    private val versionCatalog: KotlinGradleVersionCatalogExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.versionCatalog(
        action: Action<KotlinGradleVersionCatalogExtension> = Action {}
    ) {
        project.pluginManager.apply(PluginIds.Gradle.versionCatalog)
        versionCatalog.run { isEnabled = true }
        action.execute(versionCatalog)
    }
}
