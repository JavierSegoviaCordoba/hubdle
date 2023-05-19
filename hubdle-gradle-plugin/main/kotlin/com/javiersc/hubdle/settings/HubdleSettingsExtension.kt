package com.javiersc.hubdle.settings

import com.javiersc.hubdle.settings.extensions.HubdleAutoIncludeExtension
import com.javiersc.hubdle.settings.extensions.HubdleBuildScanExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.ProviderFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.property

@HubdleSettingsDslMarker
public open class HubdleSettingsExtension
@Inject
constructor(
    private val objects: ObjectFactory,
    private val providers: ProviderFactory,
) {

    public val autoInclude: HubdleAutoIncludeExtension = objects.newInstance()

    @HubdleSettingsDslMarker
    public fun autoInclude(action: Action<HubdleAutoIncludeExtension> = Action {}) {
        action.execute(autoInclude)
    }

    public val buildScan: HubdleBuildScanExtension = objects.newInstance()

    @HubdleSettingsDslMarker
    public fun buildScan(action: Action<HubdleBuildScanExtension> = Action {}) {
        action.execute(buildScan)
    }

    public val hubdleVersionCatalogVersion: Property<String> = property { "latest.release" }

    public val rootProjectName: Property<String> = property { "" }

    public val useOnAllProjects: Property<Boolean> = objects.property<Boolean>().convention(true)

    private inline fun <reified T> property(crossinline value: () -> T?): Property<T> =
        objects.property<T>().convention(providers.provider { value() })
}
