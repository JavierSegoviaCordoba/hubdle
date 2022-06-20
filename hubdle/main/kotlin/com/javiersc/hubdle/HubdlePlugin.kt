package com.javiersc.hubdle

import com.javiersc.hubdle.extensions.HubdleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

public abstract class HubdlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.extensions.create<HubdleExtension>("InternalHubdle")
    }
}
