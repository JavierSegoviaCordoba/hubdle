package com.javiersc.hubdle.extensions.kotlin.jvm

public interface KotlinJvmOptions {

    public var target: Int

    public companion object {
        public const val DefaultJvmTarget: Int = 8
        public const val DefaultGradleJvmTarget: Int = 11
    }
}
