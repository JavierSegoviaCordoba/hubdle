package com.javiersc.hubdle.project.extensions.kotlin.shared

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider

public fun Provider<MinimalExternalModuleDependency>.asString(): String =
    map { if (it.version != null) "${it.module}:${it.version}" else "${it.module}" }.get()
