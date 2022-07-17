@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.kotlin

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions.kotlin.android.HubdleKotlinAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.KotlinGradleExtension
import com.javiersc.hubdle.extensions.kotlin.intellij.HubdleIntellijExtension
import com.javiersc.hubdle.extensions.kotlin.jvm.HubdleKotlinJvmExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class HubdleKotlinExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val android: HubdleKotlinAndroidExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.android(action: Action<in HubdleKotlinAndroidExtension> = Action {}) {
        action.execute(android)
    }

    private val gradle: KotlinGradleExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.gradle(action: Action<in KotlinGradleExtension> = Action {}) {
        action.execute(this@HubdleKotlinExtension.gradle)
    }

    private val intellijPlugin: HubdleIntellijExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.intellijPlugin(action: Action<in HubdleIntellijExtension> = Action {}) {
        project.pluginManager.apply(PluginIds.JetBrains.intellij)
        intellijPlugin.run { isEnabled = true }
        action.execute(intellijPlugin)
    }

    private val jvm: HubdleKotlinJvmExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.jvm(action: Action<in HubdleKotlinJvmExtension> = Action {}) {
        pluginManager.apply(PluginIds.Kotlin.jvm)
        jvm.run { isEnabled = true }
        action.execute(jvm)
    }

    private val multiplatform: HubdleKotlinMultiplatformExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.multiplatform(
        action: Action<in HubdleKotlinMultiplatformExtension> = Action {}
    ) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)
        multiplatform.run { isEnabled = true }
        action.execute(multiplatform)
    }
}
