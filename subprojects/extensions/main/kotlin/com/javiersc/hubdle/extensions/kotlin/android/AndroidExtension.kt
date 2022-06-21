package com.javiersc.hubdle.extensions.kotlin.android

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.android.library.AndroidLibraryExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

public abstract class AndroidExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    public val library: AndroidLibraryExtension = objects.newInstance()

    public fun Project.library(action: Action<in AndroidLibraryExtension> = Action {}) {
        project.pluginManager.apply(PluginIds.Android.library)
        action.execute(library)
        hubdleState.kotlin.apply {
            android.library.isEnabled = true
            android.compileSdk = library.compileSdk
            android.minSdk = library.minSdk
        }
    }
}
