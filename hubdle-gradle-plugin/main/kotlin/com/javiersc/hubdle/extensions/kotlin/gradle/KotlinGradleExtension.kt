package com.javiersc.hubdle.extensions.kotlin.gradle

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.HubdleKotlinGradlePluginExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal.configureGradlePluginTestSourceSets
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal.configureGradlePluginTestTasks
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog.HubdleKotlinGradleVersionCatalogExtension
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

    private val plugin: HubdleKotlinGradlePluginExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.plugin(action: Action<HubdleKotlinGradlePluginExtension> = Action {}) {
        pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        pluginManager.apply(PluginIds.Gradle.javaTestFixtures)
        pluginManager.apply(PluginIds.Kotlin.jvm)
        project.configureGradlePluginTestSourceSets()
        project.configureGradlePluginTestTasks()
        plugin.run { isEnabled = true }
        action.execute(plugin)
    }

    private val versionCatalog: HubdleKotlinGradleVersionCatalogExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.versionCatalog(
        action: Action<HubdleKotlinGradleVersionCatalogExtension> = Action {}
    ) {
        project.pluginManager.apply(PluginIds.Gradle.versionCatalog)
        versionCatalog.run { isEnabled = true }
        action.execute(versionCatalog)
    }
}
