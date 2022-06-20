package com.javiersc.hubdle.extensions.kotlin.jvm

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public interface KotlinJvmOptions {

    public var target: Int

    public companion object {
        public const val DefaultJvmTarget: Int = 8
        public const val DefaultGradleJvmTarget: Int = 11
    }
}

internal fun Project.configJvmTarget(options: KotlinJvmOptions) {
    tasks.withType<KotlinCompile>().configureEach {
        it.kotlinOptions.jvmTarget = "${JavaVersion.toVersion(options.target)}"
    }
}
