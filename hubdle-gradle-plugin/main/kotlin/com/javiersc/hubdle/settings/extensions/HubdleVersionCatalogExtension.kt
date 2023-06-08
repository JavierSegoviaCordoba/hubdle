package com.javiersc.hubdle.settings.extensions

import com.javiersc.hubdle.settings.HubdleSettingsDslMarker
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property

@HubdleSettingsDslMarker
public open class HubdleVersionCatalogExtension @Inject constructor(objects: ObjectFactory) {

    public val isEnabled: Property<Boolean> = objects.property<Boolean>().convention(true)

    public fun enabled(enabled: Boolean = true) {
        isEnabled.set(enabled)
    }

    public val replaceableVersions: MapProperty<String, String> =
        objects.mapProperty<String, String>().convention(mapOf())

    @HubdleSettingsDslMarker
    public fun replaceVersion(vararg aliasVersion: Pair<String, String>) {
        replaceableVersions.putAll(aliasVersion.toMap())
    }

    public val version: Property<String> = objects.property<String>().convention("latest.release")

    @HubdleSettingsDslMarker
    public fun version(version: String) {
        this.version.set(version)
    }
}
