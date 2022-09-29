package com.javiersc.hubdle.extensions.kotlin._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configJvmTarget() {
    val target = hubdleState.kotlin.jvmVersion
    tasks.withType<JavaCompile>().configureEach { task ->
        task.targetCompatibility = "${JavaVersion.toVersion(target)}"
        task.sourceCompatibility = "${JavaVersion.toVersion(target)}"
    }

    tasks.withType<KotlinCompile>().configureEach { task ->
        task.kotlinOptions.jvmTarget = "${JavaVersion.toVersion(target)}"
    }
}
