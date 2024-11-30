package com.javiersc.hubdle.logic

import com.javiersc.gradle.properties.extensions.getStringProperty
import org.gradle.api.Project

fun Project.getSemverTagPrefixOrElse(defaultValue: String): String =
    getStringProperty("semver.tagPrefix").orNull.takeIf { it != "null" } ?: defaultValue
