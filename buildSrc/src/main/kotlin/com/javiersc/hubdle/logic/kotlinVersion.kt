package com.javiersc.hubdle.logic

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import org.gradle.api.Project

val Project.kotlinVersion: String?
    get() = getStringProperty("kotlinVersion").orNull.takeIf(String?::isNotNullNorBlank)
