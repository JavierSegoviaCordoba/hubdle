package com.javiersc.hubdle.settings

import com.javiersc.hubdle.settings.extensions.AutoIncludeExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleSettingsDslMarker
public open class HubdleSettingsExtension @Inject constructor(objects: ObjectFactory) {

    public val autoInclude: AutoIncludeExtension = objects.newInstance()

    @HubdleSettingsDslMarker
    public fun autoInclude(action: Action<AutoIncludeExtension> = Action {}) {
        action.execute(autoInclude)
    }
}
