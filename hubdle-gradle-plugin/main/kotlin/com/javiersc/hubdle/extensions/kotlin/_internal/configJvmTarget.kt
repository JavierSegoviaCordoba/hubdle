package com.javiersc.hubdle.extensions.kotlin._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configJvmTarget() {
    val target = hubdleState.kotlin.target
    tasks.withType<JavaCompile>().configureEach {
        it.targetCompatibility = "${JavaVersion.toVersion(target)}"
        it.sourceCompatibility = "${JavaVersion.toVersion(target)}"
    }

    tasks.withType<KotlinCompile>().configureEach {
        it.kotlinOptions.jvmTarget = "${JavaVersion.toVersion(target)}"
    }
}
