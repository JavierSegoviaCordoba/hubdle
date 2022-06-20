package com.javiersc.hubdle.extensions.kotlin.gradle

import com.javiersc.hubdle.extensions.HubdleDslMarker
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
        configPlugin(action)
    }

    private val versionCatalog: KotlinGradleVersionCatalogExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.versionCatalog(
        action: Action<KotlinGradleVersionCatalogExtension> = Action {}
    ) {
        configureVersionCatalog(action)
    }

    private fun Project.configPlugin(action: Action<in KotlinGradlePluginExtension>) {
        plugin.isEnabled = true
        action.execute(plugin)
        hubdleState.kotlin.gradle.plugin.isEnabled = plugin.isEnabled
        hubdleState.kotlin.target = plugin.jvmVersion
    }

    private fun Project.configureVersionCatalog(
        action: Action<in KotlinGradleVersionCatalogExtension>
    ) {
        versionCatalog.isEnabled = true
        action.execute(versionCatalog)
        hubdleState.kotlin.gradle.versionCatalog.isEnabled = versionCatalog.isEnabled
    }
}
