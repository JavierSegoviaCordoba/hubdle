package com.javiersc.hubdle.extensions.kotlin.android

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
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
        library.isEnabled = true
        action.execute(library)
        hubdleState.kotlin.android.library.isEnabled = library.isEnabled
        hubdleState.kotlin.android.compileSdk = library.compileSdk
        hubdleState.kotlin.android.minSdk = library.minSdk
    }
}
