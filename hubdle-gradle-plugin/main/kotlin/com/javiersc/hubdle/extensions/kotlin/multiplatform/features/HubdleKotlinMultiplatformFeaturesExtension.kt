package com.javiersc.hubdle.extensions.kotlin.multiplatform.features

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinComposeDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinCoroutinesDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinExtendedStdlibDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinKotestDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinMoleculeDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinSerializationDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.features.shared.HubdleKotlinSqlDelightDelegateFeatureExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.extensions.shared.features.HubdleJavaVersionDelegateFeatureExtension
import com.javiersc.hubdle.extensions.shared.features.HubdleJvmToolchainDelegateFeatureExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinMultiplatformFeaturesExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project), HubdleKotlinMultiplatformDelegateFeaturesExtension {

    override val project: Project
        get() = super.project

    public override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinMultiplatform)
}

public interface HubdleKotlinMultiplatformDelegateFeaturesExtension :
    HubdleKotlinMultiplatformMinimumTargetPerOsDelegateFeatureExtension,
    HubdleKotlinComposeDelegateFeatureExtension,
    HubdleKotlinCoroutinesDelegateFeatureExtension,
    HubdleKotlinExtendedStdlibDelegateFeatureExtension,
    HubdleJavaVersionDelegateFeatureExtension,
    HubdleKotlinKotestDelegateFeatureExtension,
    HubdleKotlinMoleculeDelegateFeatureExtension,
    HubdleKotlinSerializationDelegateFeatureExtension,
    HubdleKotlinSqlDelightDelegateFeatureExtension,
    HubdleJvmToolchainDelegateFeatureExtension

internal val HubdleEnableableExtension.hubdleKotlinMultiplatformFeatures:
    HubdleKotlinMultiplatformFeaturesExtension
    get() = getHubdleExtension()
