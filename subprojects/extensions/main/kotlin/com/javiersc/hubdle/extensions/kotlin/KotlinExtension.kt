package com.javiersc.hubdle.extensions.kotlin

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
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

public abstract class KotlinExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    public fun explicitApi(explicitApiMode: ExplicitApiMode = ExplicitApiMode.Strict) {
        hubdleState.kotlin.explicitApiMode = explicitApiMode
    }

    private val tools: ToolsExtension = objects.newInstance()

    public fun tools(action: Action<in ToolsExtension> = Action {}) {
        configTools(action)
    }

    private val android: AndroidExtension = objects.newInstance()

    private fun android(action: Action<in AndroidExtension> = Action {}) {
        configAndroid(action)
    }

    private val gradle: KotlinGradleExtension = objects.newInstance()

    public fun Project.gradle(action: Action<in KotlinGradleExtension> = Action {}) {
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

    private fun Project.configGradle(action: Action<in KotlinGradleExtension>) {
        pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        pluginManager.apply(PluginIds.Kotlin.jvm)
        action.execute(this@KotlinExtension.gradle)
        hubdleState.apply {
            kotlin.gradle.isEnabled = true
            kotlin.target = this@KotlinExtension.gradle.target
        }
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

internal val Project.kotlinExtension: KotlinProjectExtension
    get() = project.extensions.getByType()
