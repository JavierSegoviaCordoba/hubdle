package com.javiersc.hubdle

import com.javiersc.hubdle.extensions.HubdleExtension
import com.javiersc.hubdle.properties.PropertyKey
import com.javiersc.hubdle.properties.getProperty
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

public abstract class HubdlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.group = target.getProperty(PropertyKey.Project.group)

        target.extensions.create<HubdleExtension>("InternalHubdle")
    }
}
