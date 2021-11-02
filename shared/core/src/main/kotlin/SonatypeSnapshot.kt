package com.javiersc.gradle.plugins.core

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.sonatypeSnapshots() {
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
}
