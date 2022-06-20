@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.kotlin

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.android.KotlinAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.KotlinGradleExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.KotlinMultiplatformExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class KotlinExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val android: KotlinAndroidExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.android(action: Action<in KotlinAndroidExtension> = Action {}) {
        configAndroid(action)
    }

    private val gradle: KotlinGradleExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.gradle(action: Action<in KotlinGradleExtension> = Action {}) {
        configGradle(action)
    }

    private val jvm: KotlinJvmExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.jvm(action: Action<in KotlinJvmExtension> = Action {}) {
        configJvm(action)
    }

    private val multiplatform: KotlinMultiplatformExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.multiplatform(action: Action<in KotlinMultiplatformExtension> = Action {}) {
        configMultiplatform(action)
    }

    // Configurations
    private fun Project.configAndroid(action: Action<in KotlinAndroidExtension>) {
        action.execute(android)
    }

    private fun Project.configJvm(action: Action<in KotlinJvmExtension>) {
        pluginManager.apply(PluginIds.Kotlin.jvm)
        jvm.isEnabled = true
        action.execute(jvm)
        hubdleState.kotlin.jvm.isEnabled = jvm.isEnabled
        hubdleState.kotlin.target = jvm.jvmVersion
    }

    private fun Project.configGradle(action: Action<in KotlinGradleExtension>) {
        action.execute(this@KotlinExtension.gradle)
    }

    private fun Project.configMultiplatform(action: Action<in KotlinMultiplatformExtension>) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)
        multiplatform.isEnabled = true
        action.execute(multiplatform)
        hubdleState.kotlin.multiplatform.isEnabled = multiplatform.isEnabled
        hubdleState.kotlin.target = multiplatform.jvmVersion
    }
}
