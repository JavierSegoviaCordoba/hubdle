package com.javiersc.hubdle.project.extensions.kotlin.multiplatform.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinAtomicfuDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinBuildKonfigDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinComposeDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinCoroutinesDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinExtendedStdlibDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinKopyDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinKotestDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinMoleculeDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinSerializationDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinSqlDelightDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaVersionDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJvmToolchainDelegateFeatureExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinMultiplatformFeaturesExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project), HubdleKotlinMultiplatformDelegateFeaturesExtension {

    override val project: Project
        get() = super.project

    public override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)
}

public interface HubdleKotlinMultiplatformDelegateFeaturesExtension :
    HubdleJavaVersionDelegateFeatureExtension,
    HubdleJvmToolchainDelegateFeatureExtension,
    HubdleKotlinAtomicfuDelegateFeatureExtension,
    HubdleKotlinBuildKonfigDelegateFeatureExtension,
    HubdleKotlinComposeDelegateFeatureExtension,
    HubdleKotlinCoroutinesDelegateFeatureExtension,
    HubdleKotlinExtendedStdlibDelegateFeatureExtension,
    HubdleKotlinKopyDelegateFeatureExtension,
    HubdleKotlinKotestDelegateFeatureExtension,
    HubdleKotlinMoleculeDelegateFeatureExtension,
    HubdleKotlinMultiplatformMinimumTargetPerOsDelegateFeatureExtension,
    HubdleKotlinSerializationDelegateFeatureExtension,
    HubdleKotlinSqlDelightDelegateFeatureExtension

internal val HubdleEnableableExtension.hubdleKotlinMultiplatformFeatures:
    HubdleKotlinMultiplatformFeaturesExtension
    get() = getHubdleExtension()
