package com.javiersc.hubdle.extensions.config._internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

internal val Project.hasKotlinGradlePlugin: Boolean
    get() = plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0
