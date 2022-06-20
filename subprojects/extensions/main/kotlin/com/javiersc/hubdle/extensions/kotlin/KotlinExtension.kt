package com.javiersc.hubdle.extensions.kotlin

import com.javiersc.hubdle.extensions.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.internal.Kotlin
import com.javiersc.hubdle.extensions.internal.PluginIds
import com.javiersc.hubdle.extensions.internal.extensionTracker
import com.javiersc.hubdle.extensions.kotlin.android.AndroidExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.KotlinGradleExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.configJvmTarget
import com.javiersc.hubdle.extensions.kotlin.jvm.javaExtension
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
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

public abstract class KotlinExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private var explicitApiMode: ExplicitApiMode = ExplicitApiMode.Disabled

    public fun explicitApi(explicitApiMode: ExplicitApiMode = ExplicitApiMode.Strict) {
        this@KotlinExtension.explicitApiMode = explicitApiMode
    }

    private val tools: ToolsExtension = objects.newInstance()

    public fun tools(
        action: Action<in ToolsExtension> = Action {},
    ): ToolsExtension = configTools(action)

    private val android: AndroidExtension = objects.newInstance()

    public fun android(
        action: Action<in AndroidExtension> = Action {},
    ): AndroidExtension = configAndroid(action)

    private val gradle: KotlinGradleExtension = objects.newInstance()

    public fun Project.gradle(
        action: Action<in KotlinGradleExtension> = Action {},
    ): KotlinGradleExtension = configGradle(action)

    private val jvm: KotlinJvmExtension = objects.newInstance()

    public fun Project.jvm(
        action: Action<in KotlinJvmExtension> = Action {},
    ): KotlinJvmExtension = configJvm(action)

    private val multiplatform: KotlinMultiplatformExtension = objects.newInstance()

    public fun Project.multiplatform(
        action: Action<in KotlinMultiplatformExtension> = Action {},
    ): KotlinMultiplatformExtension = configMultiplatform(action)

    // Configurations
    private fun configTools(action: Action<in ToolsExtension>): ToolsExtension {
        action.execute(tools)
        return tools
    }

    private fun configAndroid(action: Action<in AndroidExtension>): AndroidExtension {
        action.execute(android)

        return android
    }

    private fun Project.configJvm(action: Action<in KotlinJvmExtension>): KotlinJvmExtension {
        pluginManager.apply(PluginIds.Kotlin.jvm)

        extensionTracker.put(Kotlin.JVM)

        action.execute(jvm)

        configureExplicitApi()

        configJvmTarget(jvm)

        javaExtension.configureDefaultJavaSourceSets()
        kotlinExtension.configureDefaultKotlinSourceSets()

        return jvm
    }

    private fun Project.configGradle(
        action: Action<in KotlinGradleExtension>
    ): KotlinGradleExtension {
        pluginManager.apply(PluginIds.Kotlin.dsl)

        extensionTracker.put(Kotlin.Gradle)

        action.execute(this@KotlinExtension.gradle)

        configureExplicitApi()

        configJvmTarget(this@KotlinExtension.gradle)

        javaExtension.configureDefaultJavaSourceSets()
        kotlinExtension.configureDefaultKotlinSourceSets()

        return this@KotlinExtension.gradle
    }

    private fun Project.configMultiplatform(
        action: Action<in KotlinMultiplatformExtension>,
    ): KotlinMultiplatformExtension {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)

        extensionTracker.put(Kotlin.Multiplatform)

        action.execute(multiplatform)

        configureExplicitApi()

        configJvmTarget(multiplatform)

        javaExtension.configureDefaultJavaSourceSets()
        kotlinExtension.configureDefaultKotlinSourceSets()

        return multiplatform
    }

    private fun Project.configureExplicitApi() {
        kotlinExtension.explicitApi = this@KotlinExtension.explicitApiMode
    }
}

private val Project.kotlinExtension: KotlinProjectExtension
    get() = project.extensions.getByType()
