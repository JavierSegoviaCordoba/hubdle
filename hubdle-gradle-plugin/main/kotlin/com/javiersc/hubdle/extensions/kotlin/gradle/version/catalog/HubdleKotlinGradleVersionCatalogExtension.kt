package com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import java.io.File
import org.gradle.api.Project

@HubdleDslMarker
public open class HubdleKotlinGradleVersionCatalogExtension : EnableableOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.gradle.versionCatalog.isEnabled
        set(value) = hubdleState.kotlin.gradle.versionCatalog.run { isEnabled = value }

    @HubdleDslMarker
    public fun Project.catalogs(vararg files: File) {
        hubdleState.kotlin.gradle.versionCatalog.catalogs += files
    }
}
