package com.javiersc.hubdle.extensions.kotlin.gradle

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.HubdleKotlinGradlePluginExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog.HubdleKotlinGradleVersionCatalogExtension
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlin
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinGradleExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    public val plugin: HubdleKotlinGradlePluginExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleKotlinGradlePluginExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }

    public val versionCatalog: HubdleKotlinGradleVersionCatalogExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun versionCatalog(
        action: Action<HubdleKotlinGradleVersionCatalogExtension> = Action {}
    ) {
        versionCatalog.enableAndExecute(action)
    }
}
