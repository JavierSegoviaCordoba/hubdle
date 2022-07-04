package com.javiersc.hubdle.settings

import org.gradle.api.Action
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenRepositoryContentDescriptor as MavenContent
import org.gradle.kotlin.dsl.maven

public fun RepositoryHandler.sonatypeSnapshot(mavenContent: Action<MavenContent> = Action {}) {
    maven("https://oss.sonatype.org/content/repositories/snapshots") { mavenContent(mavenContent) }
}
