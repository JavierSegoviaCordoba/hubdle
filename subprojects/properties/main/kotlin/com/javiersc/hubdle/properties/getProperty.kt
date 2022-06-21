package com.javiersc.hubdle.properties

import java.io.File
import org.gradle.api.Project

public fun Project.getProperty(name: String): String =
    localProperties?.getProperty(name)
        ?: localProperties?.getProperty(name.toSnakeCase()) ?: System.getenv(name)
            ?: System.getenv(name.toSnakeCase()) ?: providers.gradleProperty(name).orNull
            ?: providers.gradleProperty(name.toSnakeCase()).orNull.run {
            checkNotNull(this) {
                """
                        |The property $name is not available in any of the next sources:
                        |  - ${this@getProperty.projectDir}${File.separator}local.properties
                        |  - ${this@getProperty.rootDir}${File.separator}local.properties
                        |  - Environment variable
                        |  - ${this@getProperty.projectDir}${File.separator}gradle.properties
                        |  - ${this@getProperty.rootDir}${File.separator}gradle.properties
                        |  - ${System.getProperty("user.home")}${File.separator}.gradle${File.separator}gradle.properties
                        |  
                    """.trimMargin()
            }
        }

public fun Project.getPropertyOrNull(name: String): String? =
    runCatching { getProperty(name) }.getOrNull()

internal fun String.toSnakeCase(): String =
    map { char -> if (char.isUpperCase()) "_$char" else char.uppercaseChar() }
        .joinToString("")
        .replace(".", "_")
