package com.javiersc.hubdle.project.extensions.config.versioning.semver._internal

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.versioning.semver.hubdleSemver
import org.gradle.api.provider.Property

internal val HubdleEnableableExtension.isTagPrefixProject: Boolean
    get() {
        val projectTagPrefix: Property<String> = hubdleSemver.tagPrefix
        val tagPrefix: String = project.getStringProperty("semver.tagPrefix").orElse("").get()
        return when {
            !hubdleSemver.isFullEnabled.get() -> true
            projectTagPrefix.get() == tagPrefix -> true
            else -> false
        }
    }
