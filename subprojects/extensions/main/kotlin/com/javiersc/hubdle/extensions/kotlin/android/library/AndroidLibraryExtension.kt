package com.javiersc.hubdle.extensions.kotlin.android.library

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.PublishingOptions
import com.javiersc.hubdle.extensions.configureJavaJarsForAndroidPublishing
import com.javiersc.hubdle.extensions.configureMavenPublication
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import com.javiersc.hubdle.extensions.internal.PluginIds
import com.javiersc.hubdle.extensions.kotlin.android.AndroidOptions
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

public open class AndroidLibraryExtension : AndroidOptions, PublishingOptions {

    override fun Project.publishing() {
        pluginManager.apply(PluginIds.Publishing.mavenPublish)
        pluginManager.apply(PluginIds.Publishing.signing)
        configurePublishingExtension()
        configureMavenPublication("release")
        configureJavaJarsForAndroidPublishing()
        configureSigningForPublishing()
    }

    override var compileSdk: Int = AndroidOptions.DefaultCompileSdk

    override var minSdk: Int = AndroidOptions.DefaultMinSdk

    override var target: Int = KotlinJvmOptions.DefaultJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<out AndroidSourceSet>
        get() = the<LibraryExtension>().sourceSets
}
