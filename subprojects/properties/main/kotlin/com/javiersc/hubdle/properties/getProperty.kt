package com.javiersc.hubdle.properties

import org.gradle.api.Project

public fun Project.getProperty(name: String): String =
    localProperties?.getProperty(name)
        ?: localProperties?.getProperty(name.toSnakeCase()) ?: System.getenv(name)
            ?: System.getenv(name.toSnakeCase()) ?: providers.gradleProperty(name).orNull
            ?: providers.gradleProperty(name.toSnakeCase()).get()

public fun Project.getPropertyOrNull(name: String): String? =
    runCatching { getProperty(name) }.getOrNull()

internal fun String.toSnakeCase(): String =
    map { char -> if (char.isUpperCase()) "_$char" else char.uppercaseChar() }
        .joinToString("")
        .replace(".", "_")
