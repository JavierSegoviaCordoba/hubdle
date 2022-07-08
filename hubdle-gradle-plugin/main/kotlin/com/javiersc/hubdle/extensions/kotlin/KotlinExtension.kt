@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.kotlin

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.android.KotlinAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.KotlinGradleExtension
import com.javiersc.hubdle.extensions.kotlin.intellij.IntellijExtension
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
        action.execute(android)
    }

    private val gradle: KotlinGradleExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.gradle(action: Action<in KotlinGradleExtension> = Action {}) {
        action.execute(this@KotlinExtension.gradle)
    }

    private val intellijPlugin: IntellijExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.intellijPlugin(action: Action<in IntellijExtension> = Action {}) {
        project.pluginManager.apply(PluginIds.JetBrains.intellij)
        hubdleState.kotlin.target = intellijPlugin.jvmVersion
        intellijPlugin.isEnabled = true
        action.execute(intellijPlugin)
        hubdleState.kotlin.intellij.isEnabled = intellijPlugin.isEnabled
    }

    private val jvm: KotlinJvmExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.jvm(action: Action<in KotlinJvmExtension> = Action {}) {
        pluginManager.apply(PluginIds.Kotlin.jvm)
        jvm.isEnabled = true
        action.execute(jvm)
        hubdleState.kotlin.jvm.isEnabled = jvm.isEnabled
        hubdleState.kotlin.target = jvm.jvmVersion
    }

    private val multiplatform: KotlinMultiplatformExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.multiplatform(action: Action<in KotlinMultiplatformExtension> = Action {}) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)
        multiplatform.isEnabled = true
        action.execute(multiplatform)
        hubdleState.kotlin.multiplatform.isEnabled = multiplatform.isEnabled
        hubdleState.kotlin.target = multiplatform.jvmVersion
    }
}
