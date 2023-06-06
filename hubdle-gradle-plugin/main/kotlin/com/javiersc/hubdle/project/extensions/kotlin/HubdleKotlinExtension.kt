package com.javiersc.hubdle.project.extensions.kotlin

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.android.HubdleKotlinAndroidExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.application.hubdleAndroidApplication
import com.javiersc.hubdle.project.extensions.kotlin.android.library.hubdleAndroidLibrary
import com.javiersc.hubdle.project.extensions.kotlin.compiler.options.HubdleKotlinCompilerOptionsExtension
import com.javiersc.hubdle.project.extensions.kotlin.features.HubdleKotlinFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.jvm.HubdleKotlinJvmExtension
import com.javiersc.hubdle.project.extensions.kotlin.jvm.hubdleKotlinJvm
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.HubdleKotlinMultiplatformExtension
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.hubdleKotlinMultiplatformAndroid
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleKotlinExtension
@Inject
constructor(
    project: Project,
) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    public val features: HubdleKotlinFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun features(action: Action<HubdleKotlinFeaturesExtension> = Action {}) {
        features.enableAndExecute(action)
    }

    public val android: HubdleKotlinAndroidExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun android(action: Action<HubdleKotlinAndroidExtension> = Action {}) {
        android.enableAndExecute(action)
    }

    public val compilerOptions: HubdleKotlinCompilerOptionsExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun compilerOptions(action: Action<HubdleKotlinCompilerOptionsExtension> = Action {}) {
        compilerOptions.enableAndExecute(action)
    }

    public val jvm: HubdleKotlinJvmExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun jvm(action: Action<HubdleKotlinJvmExtension> = Action {}) {
        jvm.enableAndExecute(action)
    }

    public val multiplatform: HubdleKotlinMultiplatformExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun multiplatform(action: Action<HubdleKotlinMultiplatformExtension> = Action {}) {
        multiplatform.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleKotlin: HubdleKotlinExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdleKotlinAny: Set<HubdleConfigurableExtension>
    get() =
        setOf(
            hubdleAndroidApplication,
            hubdleAndroidLibrary,
            hubdleKotlinJvm,
            hubdleKotlinMultiplatform,
        )

internal val HubdleEnableableExtension.hubdleAndroidAny: Set<HubdleConfigurableExtension>
    get() =
        setOf(
            hubdleAndroidApplication,
            hubdleAndroidLibrary,
            hubdleKotlinMultiplatformAndroid,
        )
