package com.javiersc.hubdle.extensions.kotlin.gradle

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
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
        pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        pluginManager.apply(PluginIds.Kotlin.jvm)
        plugin.isEnabled = true
        action.execute(plugin)
        hubdleState.kotlin.gradle.plugin.isEnabled = plugin.isEnabled
        hubdleState.kotlin.target = plugin.jvmVersion
    }

    private val versionCatalog: KotlinGradleVersionCatalogExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.versionCatalog(
        action: Action<KotlinGradleVersionCatalogExtension> = Action {}
    ) {
        project.pluginManager.apply(PluginIds.Gradle.versionCatalog)
        versionCatalog.isEnabled = true
        action.execute(versionCatalog)
        hubdleState.kotlin.gradle.versionCatalog.isEnabled = versionCatalog.isEnabled
    }
}
