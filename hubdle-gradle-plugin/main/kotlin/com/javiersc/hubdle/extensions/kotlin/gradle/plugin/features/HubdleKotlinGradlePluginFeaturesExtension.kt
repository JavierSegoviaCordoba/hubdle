package com.javiersc.hubdle.extensions.kotlin.gradle.plugin.features

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinExtendedStdlibDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinKotestDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin.hubdleGradlePlugin
import com.javiersc.hubdle.extensions.shared.features.HubdleJavaVersionDelegateFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.HubdleJvmToolchainDelegateFeatureExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinGradlePluginFeaturesExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project), HubdleKotlinGradlePluginDelegateFeaturesExtension {

    override val project: Project
        get() = super.project

    public override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleGradlePlugin)
}

public interface HubdleKotlinGradlePluginDelegateFeaturesExtension :
    HubdleKotlinExtendedStdlibDelegateFeatureExtension,
    HubdleJavaVersionDelegateFeatureExtension,
    HubdleKotlinKotestDelegateFeatureExtension,
    HubdleJvmToolchainDelegateFeatureExtension

internal val HubdleEnableableExtension.hubdleGradlePluginFeatures:
    HubdleKotlinGradlePluginFeaturesExtension
    get() = getHubdleExtension()
