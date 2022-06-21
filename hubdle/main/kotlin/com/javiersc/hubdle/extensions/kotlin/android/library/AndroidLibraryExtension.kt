package com.javiersc.hubdle.extensions.kotlin.android.library

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.PublishingOptions
import com.javiersc.hubdle.extensions.RawConfigOptions
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.android.AndroidOptions
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

public open class AndroidLibraryExtension :
    AndroidOptions, PublishingOptions, RawConfigOptions<LibraryExtension> {

    override fun Project.publishing() {
        pluginManager.apply(PluginIds.Publishing.mavenPublish)
        pluginManager.apply(PluginIds.Publishing.signing)
        hubdleState.kotlin.isPublishingEnabled = true
    }

    override var compileSdk: Int = AndroidOptions.DefaultCompileSdk

    override var minSdk: Int = AndroidOptions.DefaultMinSdk

    override var target: Int = KotlinJvmOptions.DefaultJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<out AndroidSourceSet>
        get() = the<LibraryExtension>().sourceSets

    override fun Project.rawConfig(action: Action<LibraryExtension>) {
        project.pluginManager.apply(PluginIds.Android.library)
        project.pluginManager.apply(PluginIds.Kotlin.jvm)
        action.execute(the())
    }
}
