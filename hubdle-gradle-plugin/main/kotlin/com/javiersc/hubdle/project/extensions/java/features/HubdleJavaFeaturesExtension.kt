package com.javiersc.hubdle.project.extensions.java.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.java.hubdleJava
import com.javiersc.hubdle.project.extensions.shared.features.HubdleIntellijDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaApplicationDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaVersionDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJvmToolchainDelegateFeatureExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleJavaFeaturesExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project), HubdleJavaDelegateFeaturesExtension {

    override val project: Project
        get() = super.project

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleJava)
}

internal val HubdleEnableableExtension.hubdleJavaFeatures: HubdleJavaFeaturesExtension
    get() = getHubdleExtension()

public interface HubdleJavaDelegateFeaturesExtension :
    HubdleIntellijDelegateFeatureExtension,
    HubdleJavaApplicationDelegateFeatureExtension,
    HubdleJavaVersionDelegateFeatureExtension,
    HubdleJvmToolchainDelegateFeatureExtension
