package com.javiersc.hubdle.extensions.kotlin.android

import com.android.build.gradle.LibraryExtension
import com.javiersc.hubdle.extensions.configDefaultAndroidSourceSets
import com.javiersc.hubdle.extensions.internal.Kotlin
import com.javiersc.hubdle.extensions.internal.PluginIds
import com.javiersc.hubdle.extensions.internal.extensionTracker
import com.javiersc.hubdle.extensions.kotlin.android.library.AndroidLibraryExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the

public abstract class AndroidExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val library: AndroidLibraryExtension = objects.newInstance()

    public fun Project.library(
        action: Action<in AndroidLibraryExtension> = Action {},
    ): AndroidLibraryExtension {
        extensionTracker.put(Kotlin.Android.Library)

        project.pluginManager.apply(PluginIds.Android.library)

        action.execute(library)

        project.the<LibraryExtension>().apply {
            compileSdk = library.compileSdk
            defaultConfig.minSdk = library.minSdk

            sourceSets.all { it.configDefaultAndroidSourceSets() }
        }

        return library
    }
}
