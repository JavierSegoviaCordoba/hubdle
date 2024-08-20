package com.javiersc.hubdle.project.extensions.kotlin.jvm.features

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinComposeDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinContextReceiversDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinCoroutinesDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinExtendedStdlibDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinKotestDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinMoleculeDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinSerializationDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.shared.HubdleKotlinSqlDelightDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.shared.features.HubdleGradleDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleIntellijDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaApplicationDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaVersionDelegateFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJvmToolchainDelegateFeatureExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinJvmFeaturesExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project), HubdleKotlinJvmDelegateFeaturesExtension {

    override val project: Project
        get() = super.project

    public override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinJvm)
}

public interface HubdleKotlinJvmDelegateFeaturesExtension :
    HubdleGradleDelegateFeatureExtension,
    HubdleIntellijDelegateFeatureExtension,
    HubdleJavaApplicationDelegateFeatureExtension,
    HubdleKotlinCompilerPluginDelegateFeatureExtension,
    HubdleKotlinComposeDelegateFeatureExtension,
    HubdleKotlinContextReceiversDelegateFeatureExtension,
    HubdleKotlinCoroutinesDelegateFeatureExtension,
    HubdleKotlinExtendedStdlibDelegateFeatureExtension,
    HubdleJavaVersionDelegateFeatureExtension,
    HubdleKotlinKotestDelegateFeatureExtension,
    HubdleKotlinMoleculeDelegateFeatureExtension,
    HubdleKotlinSerializationDelegateFeatureExtension,
    HubdleKotlinSqlDelightDelegateFeatureExtension,
    HubdleJvmToolchainDelegateFeatureExtension

internal val HubdleEnableableExtension.hubdleKotlinJvmFeatures: HubdleKotlinJvmFeaturesExtension
    get() = getHubdleExtension()
