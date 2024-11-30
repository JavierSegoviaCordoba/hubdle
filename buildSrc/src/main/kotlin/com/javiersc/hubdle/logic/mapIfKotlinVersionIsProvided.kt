package com.javiersc.hubdle.logic

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.gradle.version.isSnapshot
import org.gradle.api.Project

fun Project.mapIfKotlinVersionIsProvided(version: GradleVersion, kotlinVersion: String): String {
    val major: Int = version.major
    val minor: Int = version.minor
    val patch: Int = version.patch

    val isKotlinDevVersion = kotlinVersion.isKotlinDevVersion() || kotlinVersion.contains("dev")
    val isSnapshotStage =
        version.isSnapshot || getStringProperty("semver.stage").orNull?.isSnapshot == true

    val newVersion: String =
        if (isKotlinDevVersion || isSnapshotStage) {
            "$major.$minor.$patch+$kotlinVersion-SNAPSHOT"
        } else {
            "$major.$minor.$patch+$kotlinVersion"
        }
    return newVersion
}

private fun String.isKotlinDevVersion(): Boolean =
    matches(Regex("""(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)-dev-(0|[1-9]\d*)"""))
