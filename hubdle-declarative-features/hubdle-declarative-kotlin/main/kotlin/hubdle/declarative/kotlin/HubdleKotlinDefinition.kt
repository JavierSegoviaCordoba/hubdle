@file:Suppress("UnstableApiUsage")

package hubdle.declarative.kotlin

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.Definition

public interface HubdleKotlinDefinition : HubdleDefinition, Definition<HubdleKotlinBuildModel> {

    override val featureName: String
        get() = "kotlin"
}
