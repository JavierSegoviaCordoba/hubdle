package com.javiersc.hubdle.extensions.kotlin.android

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.android.library.AndroidLibraryExtension
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

@HubdleDslMarker
public abstract class AndroidExtension
@Inject
constructor(
    objects: ObjectFactory,
) : RawConfigOptions<KotlinJvmProjectExtension> {

    public val library: AndroidLibraryExtension = objects.newInstance()

    public fun Project.library(action: Action<in AndroidLibraryExtension> = Action {}) {
        project.pluginManager.apply(PluginIds.Android.library)
        project.pluginManager.apply(PluginIds.Kotlin.jvm)
        action.execute(library)
        hubdleState.kotlin.apply {
            android.library.isEnabled = true
            android.compileSdk = library.compileSdk
            android.minSdk = library.minSdk
        }
    }

    override fun Project.rawConfig(action: Action<KotlinJvmProjectExtension>) {
        project.pluginManager.apply(PluginIds.Kotlin.jvm)
        action.execute(the())
    }
}
