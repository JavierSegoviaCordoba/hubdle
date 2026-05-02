@file:Suppress("UnstableApiUsage")

package com.javiersc.hubdle.declarative

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.features.annotations.RegistersProjectFeatures

@RegistersProjectFeatures(HubdleProjectType::class)
public open class HubdleDeclarativePlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        // NO-OP
    }
}
