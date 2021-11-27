@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.docs.internal

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

val Project.hasKotlinGradlePlugin: Boolean
    get() = plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0
