@file:Suppress("UnstableApiUsage")

package hubdle.declarative

import hubdle.declarative.config.HubdleConfigFeature
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.features.annotations.RegistersProjectFeatures

@RegistersProjectFeatures(HubdleProjectType::class, HubdleConfigFeature::class)
public open class HubdleDeclarativePlugin : Plugin<Settings> {

    override fun apply(target: Settings) {
        // NO-OP
    }
}
