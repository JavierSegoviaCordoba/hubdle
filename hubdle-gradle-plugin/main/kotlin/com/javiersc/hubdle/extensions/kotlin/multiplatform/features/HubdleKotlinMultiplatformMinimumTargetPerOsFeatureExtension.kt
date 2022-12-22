package com.javiersc.hubdle.extensions.kotlin.multiplatform.features

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public open class HubdleKotlinMultiplatformMinimumTargetPerOsFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)
}

public interface HubdleKotlinMultiplatformMinimumTargetPerOsDelegateFeatureExtension :
    BaseHubdleDelegateExtension {

    public val minimumTargetPerOs: HubdleKotlinMultiplatformMinimumTargetPerOsFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun minimumTargetPerOs(
        action: Action<HubdleKotlinMultiplatformMinimumTargetPerOsFeatureExtension> = Action {}
    ) {
        minimumTargetPerOs.enableAndExecute(action)
    }
}

internal fun HubdleConfigurableExtension.configurableTargetPerOs(
    operativeSystem: Boolean,
    block: KotlinMultiplatformExtension.() -> Unit
) {
    val isEnabledButNotPerOS = property {
        (isEnabled.get() && !hubdleMinimumTargetPerOs.isFullEnabled.get())
    }
    val isEnabledPerOS = property {
        hubdleMinimumTargetPerOs.isFullEnabled.get() && operativeSystem
    }
    val isEnabled = property { isEnabledButNotPerOS.get() || isEnabledPerOS.get() }
    configurable(isEnabled = isEnabled, priority = priority) {
        block(project.extensions.getByType())
    }
}

internal val HubdleEnableableExtension.hubdleMinimumTargetPerOs:
    HubdleKotlinMultiplatformMinimumTargetPerOsFeatureExtension
    get() = getHubdleExtension()
