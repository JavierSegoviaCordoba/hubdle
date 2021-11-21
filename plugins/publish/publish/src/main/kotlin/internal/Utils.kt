@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.publish.internal

import com.javiersc.kotlin.stdlib.AnsiColor
import com.javiersc.kotlin.stdlib.ansiColor
import org.gradle.api.Project

internal fun Project.getVariable(propertyName: String, environmentVariableName: String): String? {
    val property: String? = project.properties[propertyName]?.toString()
    val environmentVariable: String? = System.getenv(environmentVariableName)

    if (isPublishing && property.isNullOrBlank() && environmentVariable.isNullOrBlank()) {
        warningMessage(
            "$propertyName Gradle property and " +
                "$environmentVariableName environment variable are missing",
        )
    }
    return property ?: environmentVariable
}

internal val Project.isPublishing: Boolean
    get() = gradle.startParameter.taskNames.any { it.startsWith("publish") }

internal fun Project.warningMessage(message: String) =
    logger.lifecycle(message.ansiColor(AnsiColor.Foreground.Yellow))
