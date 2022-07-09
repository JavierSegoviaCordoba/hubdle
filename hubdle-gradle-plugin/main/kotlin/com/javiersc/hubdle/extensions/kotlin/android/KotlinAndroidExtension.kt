package com.javiersc.hubdle.extensions.kotlin.android

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions.kotlin.android.library.KotlinAndroidLibraryExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class KotlinAndroidExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    public val library: KotlinAndroidLibraryExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.library(action: Action<in KotlinAndroidLibraryExtension> = Action {}) {
        pluginManager.apply(PluginIds.Android.library)
        pluginManager.apply(PluginIds.Android.kotlin)
        library.run { isEnabled = true }
        action.execute(library)
    }
}
