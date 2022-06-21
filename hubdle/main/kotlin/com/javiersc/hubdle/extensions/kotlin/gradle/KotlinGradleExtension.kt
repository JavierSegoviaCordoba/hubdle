package com.javiersc.hubdle.extensions.kotlin.gradle

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.GradlePluginExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog.GradleVersionCatalogExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

public open class KotlinGradleExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val plugin: GradlePluginExtension = objects.newInstance()

    public fun Project.plugin(action: Action<GradlePluginExtension> = Action {}) {
        configPlugin(action)
    }

    private val versionCatalog: GradleVersionCatalogExtension = objects.newInstance()

    public fun Project.versionCatalog(action: Action<GradleVersionCatalogExtension> = Action {}) {
        configureVersionCatalog(action)
    }

    private fun Project.configPlugin(action: Action<in GradlePluginExtension>) {
        pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        pluginManager.apply(PluginIds.Kotlin.jvm)
        action.execute(this@KotlinGradleExtension.plugin)
        hubdleState.apply {
            kotlin.gradle.plugin.isEnabled = true
            kotlin.target = this@KotlinGradleExtension.plugin.target
        }
    }

    private fun Project.configureVersionCatalog(action: Action<in GradleVersionCatalogExtension>) {
        pluginManager.apply(PluginIds.Gradle.versionCatalog)
        action.execute(this@KotlinGradleExtension.versionCatalog)
        hubdleState.apply { kotlin.gradle.versionCatalog.isEnabled = true }
    }
}
