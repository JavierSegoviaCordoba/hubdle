package com.javiersc.hubdle.extensions.kotlin.android

import com.android.build.api.dsl.AndroidSourceSet
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.options.SourceDirectoriesOptions

public interface AndroidOptions : KotlinJvmOptions, SourceDirectoriesOptions<AndroidSourceSet> {

    public var compileSdk: Int

    public var minSdk: Int

    public companion object {
        public const val COMPILE_SDK: Int = 31
        public const val MIN_SDK: Int = 21
    }
}
