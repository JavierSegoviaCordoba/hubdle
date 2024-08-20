package com.javiersc.hubdle.project.extensions.shared.android

import com.android.build.api.dsl.TestOptions
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.android._internal.androidCommonExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.android.features.HubdleKotlinAndroidBuildFeaturesExtension
import org.gradle.api.Action
import org.gradle.api.artifacts.Configuration

public interface HubdleAndroidDelegateSharedApis {

    @HubdleDslMarker
    public fun HubdleEnableableExtension.testOptions(action: Action<TestOptions>) {
        lazyConfigurable {
            val common = project.androidCommonExtension()
            action.execute(common.testOptions)
        }
    }

    @HubdleDslMarker
    public fun HubdleEnableableExtension.configuration(
        name: String,
        action: Action<Configuration>,
    ) {
        lazyConfigurable { project.configurations.named(name, action::execute) }
    }

    public val HubdleEnableableExtension.buildFeatures: HubdleKotlinAndroidBuildFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun HubdleEnableableExtension.buildFeatures(
        action: Action<HubdleKotlinAndroidBuildFeaturesExtension> = Action {}
    ) {
        buildFeatures.enableAndExecute(action)
    }
}
