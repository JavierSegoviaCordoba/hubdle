@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.core

import com.javiersc.semver.Version
import org.gradle.api.Project

val Project.isSignificant: Boolean
    get() = Version.regex.matches("$version")

val Project.onlySignificantGradleProperty: Boolean
    get() = properties["publish.onlySignificant"]?.toString()?.toBoolean() ?: true
