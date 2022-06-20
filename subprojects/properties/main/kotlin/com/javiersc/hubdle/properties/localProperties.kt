package com.javiersc.hubdle.properties

import java.io.File
import java.util.Properties
import org.gradle.api.Project

public val Project.localProperties: Properties?
    get() {
        val projectLocalProps: File = file("local.properties")
        val rootProjectLocalProps: File = rootProject.file("local.properties")
        return when {
            projectLocalProps.exists() -> {
                Properties().apply { load(projectLocalProps.inputStream()) }
            }
            rootProjectLocalProps.exists() -> {
                Properties().apply { load(rootProjectLocalProps.inputStream()) }
            }
            else -> null
        }
    }
