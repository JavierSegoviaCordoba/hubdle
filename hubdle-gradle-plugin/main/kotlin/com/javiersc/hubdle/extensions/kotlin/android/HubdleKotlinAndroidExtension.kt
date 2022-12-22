package com.javiersc.hubdle.extensions.kotlin.android

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.android.application.HubdleKotlinAndroidApplicationExtension
import com.javiersc.hubdle.extensions.kotlin.android.application.hubdleAndroidApplication
import com.javiersc.hubdle.extensions.kotlin.android.features.HubdleKotlinAndroidBuildFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.android.features.HubdleKotlinAndroidFeaturesExtension
import com.javiersc.hubdle.extensions.kotlin.android.library.HubdleKotlinAndroidLibraryExtension
import com.javiersc.hubdle.extensions.kotlin.android.library.hubdleAndroidLibrary
import com.javiersc.hubdle.extensions.kotlin.hubdleKotlin
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

// TODO: transform into `HubdleConfigurableExtension`
@HubdleDslMarker
public open class HubdleKotlinAndroidExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    public val namespace: Property<String?> = property { null }

    public val minSdk: Property<Int> = property { 23 }

    public val compileSdk: Property<Int> = property { 33 }

    public val targetSdk: Property<Int> = property { 33 }

    public val buildFeatures: HubdleKotlinAndroidBuildFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun buildFeatures(
        action: Action<HubdleKotlinAndroidBuildFeaturesExtension> = Action {}
    ) {
        buildFeatures.enableAndExecute(action)
    }

    public val features: HubdleKotlinAndroidFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun features(action: Action<HubdleKotlinAndroidFeaturesExtension> = Action {}) {
        features.enableAndExecute(action)
    }

    public val application: HubdleKotlinAndroidApplicationExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun application(action: Action<HubdleKotlinAndroidApplicationExtension> = Action {}) {
        application.enableAndExecute(action)
    }

    public val library: HubdleKotlinAndroidLibraryExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun library(action: Action<HubdleKotlinAndroidLibraryExtension> = Action {}) {
        library.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleAndroid: HubdleKotlinAndroidExtension
    get() = getHubdleExtension()

internal val Project.hubdleAndroid: HubdleKotlinAndroidExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdleAndroidAny: Set<HubdleConfigurableExtension>
    get() =
        setOf(
            hubdleAndroidApplication,
            hubdleAndroidLibrary,
        )

internal val Project.hubdleAndroidAny: Set<HubdleConfigurableExtension>
    get() =
        setOf(
            hubdleAndroidApplication,
            hubdleAndroidLibrary,
        )
