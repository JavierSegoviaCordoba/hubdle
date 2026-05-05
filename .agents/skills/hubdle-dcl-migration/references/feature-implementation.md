# Feature Implementation

Use this reference when implementing the four core files for a Hubdle Declarative feature.

## `Feature`

Pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.features.annotations.BindsProjectFeature
import org.gradle.features.binding.ProjectFeatureBinding
import org.gradle.features.binding.ProjectFeatureBindingBuilder
import org.gradle.features.dsl.bindProjectFeature

@BindsProjectFeature(HubdleConfigFeature::class)
public open class HubdleConfigFeature : Plugin<Project>, ProjectFeatureBinding {

    override fun apply(target: Project) {
        // NO-OP
    }

    override fun bind(builder: ProjectFeatureBindingBuilder) {
        builder
            .bindProjectFeature(HubdleConfigApplyAction.NAME, HubdleConfigApplyAction::class)
            .withUnsafeDefinition()
            .withUnsafeApplyAction()
    }
}
```

Rules:

- Binding name is the DCL block name.
- Keep feature classes public if `HubdleDeclarativePlugin` registers them.
- Keep actual behavior in `ApplyAction`, not in `Plugin<Project>.apply`.

## `Definition`

Pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.features.binding.Definition

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val group: Property<String>
}
```

Rules:

- Extend `HubdleDefinition` for Hubdle features.
- Set `featureName` to the DSL block name.
- Use `enabled: Property<Boolean>` from `HubdleDefinition`.
- Add only declarative user-facing state here.
- Use typed managed Gradle properties.
- Use `@get:Nested` for nested definition objects and managed containers.
- Do not add arbitrary action/lambda methods.
- Do not use `isEnabled: Property<Boolean>` in DCL definitions.
- For factory functions and DCL annotations, read
  [`dcl-annotations.md`](dcl-annotations.md).

## `BuildModel`

Pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleConfigBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
```

Rules:

- Use `HubdleBuildModel` for features that need parent-aware enablement.
- Use `effectiveEnabled` to pass enablement to child features.
- Use `BuildModel.None` only for features with no derived state and no child state needs.

## `ApplyAction`

Pattern:

```kotlin
@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleRootDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleConfigApplyAction :
    ProjectFeatureApplyAction<HubdleConfigDefinition, HubdleConfigBuildModel, HubdleRootDefinition>,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleConfigDefinition,
        buildModel: HubdleConfigBuildModel,
        parentDefinition: HubdleRootDefinition,
    ) = definition.run {
        val featureEffectiveEnabled =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }
            // Configure project behavior here.
        }
    }

    companion object {
        const val NAME = "config"
    }
}
```

Rules:

- Direct children of `hubdle {}` use `HubdleRootDefinition` as parent.
- Nested features use the immediate parent definition.
- Compute `featureEffectiveEnabled` first.
- Do nothing when disabled.
- Use `definition.someProperty.orElse(default)` for defaults.
- Do not call `convention(...)` on finalized DCL definitions inside apply actions.
- Apply owned plugins with `pluginManager.apply(...)`.
- Configure optional plugins with `plugins.withId(...)`.
- Reuse Kotlin DSL behavior through shared provider/value based configurers.
