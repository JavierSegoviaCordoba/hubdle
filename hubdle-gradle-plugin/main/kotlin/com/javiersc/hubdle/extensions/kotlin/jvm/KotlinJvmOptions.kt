package com.javiersc.hubdle.extensions.kotlin.jvm

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project

public interface KotlinJvmOptions {

    public var Project.jvmVersion: Int
        get() = hubdleState.kotlin.jvmVersion
        set(value) = hubdleState.kotlin.run { jvmVersion = value }

    public companion object {
        public const val JVM_VERSION: Int = 8
    }
}
