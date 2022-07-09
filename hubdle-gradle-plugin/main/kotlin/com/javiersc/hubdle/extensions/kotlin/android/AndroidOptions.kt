package com.javiersc.hubdle.extensions.kotlin.android

import com.android.build.api.dsl.AndroidSourceSet
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.options.SourceDirectoriesOptions

public interface AndroidOptions :
    AndroidSdkOptions, KotlinJvmOptions, SourceDirectoriesOptions<AndroidSourceSet>
