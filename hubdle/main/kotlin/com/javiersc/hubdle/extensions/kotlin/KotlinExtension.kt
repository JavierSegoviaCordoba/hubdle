package com.javiersc.hubdle.extensions.kotlin

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.android.AndroidExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.KotlinGradleExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.KotlinMultiplatformExtension
import com.javiersc.hubdle.extensions.kotlin.tools.ToolsExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public abstract class KotlinExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val tools: ToolsExtension = objects.newInstance()

    public fun tools(action: Action<in ToolsExtension> = Action {}) {
        configTools(action)
    }

    private val android: AndroidExtension = objects.newInstance()

    private fun android(action: Action<in AndroidExtension> = Action {}) {
        configAndroid(action)
    }

    private val gradle: KotlinGradleExtension = objects.newInstance()

    public fun gradle(action: Action<in KotlinGradleExtension> = Action {}) {
        configGradle(action)
    }

    private val jvm: KotlinJvmExtension = objects.newInstance()

    public fun Project.jvm(action: Action<in KotlinJvmExtension> = Action {}) {
        configJvm(action)
    }

    private val multiplatform: KotlinMultiplatformExtension = objects.newInstance()

    public fun Project.multiplatform(action: Action<in KotlinMultiplatformExtension> = Action {}) {
        configMultiplatform(action)
    }

    // Configurations
    private fun configTools(action: Action<in ToolsExtension>) {
        action.execute(tools)
    }

    private fun configAndroid(action: Action<in AndroidExtension>) {
        action.execute(android)
    }

    private fun Project.configJvm(action: Action<in KotlinJvmExtension>) {
        pluginManager.apply(PluginIds.Kotlin.jvm)
        action.execute(jvm)
        hubdleState.apply {
            kotlin.jvm.isEnabled = true
            kotlin.target = jvm.target
        }
    }

    private fun configGradle(action: Action<in KotlinGradleExtension>) {
        action.execute(this@KotlinExtension.gradle)
    }

    private fun Project.configMultiplatform(action: Action<in KotlinMultiplatformExtension>) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)
        action.execute(multiplatform)
        hubdleState.apply {
            kotlin.multiplatform.isEnabled = true
            kotlin.target = this@KotlinExtension.multiplatform.target
        }
    }
}
