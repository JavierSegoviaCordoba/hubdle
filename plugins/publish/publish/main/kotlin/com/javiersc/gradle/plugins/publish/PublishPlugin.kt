package com.javiersc.gradle.plugins.publish

import com.javiersc.gradle.plugins.publish.internal.configurePublishOnlySignificant
import com.javiersc.gradle.plugins.publish.internal.configurePublishing
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class PublishPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("org.gradle.maven-publish")
        target.pluginManager.apply("org.gradle.signing")

        target.configurePublishing()
        target.configurePublishOnlySignificant()
    }
}
