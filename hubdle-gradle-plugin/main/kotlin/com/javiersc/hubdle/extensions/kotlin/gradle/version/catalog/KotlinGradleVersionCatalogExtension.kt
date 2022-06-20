package com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import java.io.File
import org.gradle.api.Project

@HubdleDslMarker
public open class KotlinGradleVersionCatalogExtension : EnableableOptions {

    override var isEnabled: Boolean = IS_ENABLED

    @HubdleDslMarker
    public fun Project.catalogs(vararg files: File) {
        hubdleState.kotlin.gradle.versionCatalog.catalogs += files
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
