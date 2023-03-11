package com.javiersc.hubdle.settings.extensions

import com.javiersc.hubdle.settings.HubdleSettingsDslMarker
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

@HubdleSettingsDslMarker
public open class HubdleBuildScanExtension @Inject constructor(objects: ObjectFactory) {

    public val isEnabled: Property<Boolean> = objects.property<Boolean>().convention(true)

    public fun enabled(enabled: Boolean = true) {
        isEnabled.set(enabled)
    }

    public val publishAlways: Property<Boolean> =
        objects.property<Boolean>().convention(System.getenv("CI") != null)
}
