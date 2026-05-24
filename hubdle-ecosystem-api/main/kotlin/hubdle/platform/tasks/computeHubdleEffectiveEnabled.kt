@file:Suppress("UnstableApiUsage")

package hubdle.platform.tasks

import hubdle.platform.HubdleBuildModel
import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Provider
import org.gradle.features.binding.BuildModel
import org.gradle.features.binding.Definition
import org.gradle.features.binding.ProjectFeatureApplicationContext

public fun <
    PARENT_BUILD_MODEL,
    PARENT_DEFINITION,
    FEATURE_BUILD_MODEL,
    FEATURE_DEFINITION,
> ProjectFeatureApplicationContext.computeHubdleEffectiveEnabled(
    parentDefinition: PARENT_DEFINITION,
    featureDefinition: FEATURE_DEFINITION,
    defaultEnabled: Boolean,
): Provider<Boolean>
    where
        PARENT_BUILD_MODEL : BuildModel,
        PARENT_BUILD_MODEL : HubdleBuildModel,
        PARENT_DEFINITION : Definition<PARENT_BUILD_MODEL>,
        PARENT_DEFINITION : HubdleDefinition,
        FEATURE_BUILD_MODEL : BuildModel,
        FEATURE_BUILD_MODEL : HubdleBuildModel,
        FEATURE_DEFINITION : Definition<FEATURE_BUILD_MODEL>,
        FEATURE_DEFINITION : HubdleDefinition {

    val parentBuildModel: PARENT_BUILD_MODEL = getBuildModel(parentDefinition)
    val parentEffectiveEnabled: Provider<Boolean> = parentBuildModel.effectiveEnabled

    val declaredEnabled: Provider<Boolean> = featureDefinition.enabled.orElse(defaultEnabled)

    val featureEffectiveEnabled: Provider<Boolean> =
        parentEffectiveEnabled.zip(declaredEnabled) { parentEnabled, declaredEnabled ->
            if (parentEnabled) declaredEnabled else false
        }

    val featureBuildModel: FEATURE_BUILD_MODEL = getBuildModel(featureDefinition)
    featureBuildModel.effectiveEnabled.set(featureEffectiveEnabled)

    return featureBuildModel.effectiveEnabled
}
