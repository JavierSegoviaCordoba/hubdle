package com.javiersc.hubdle.extensions.kotlin.android.library

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.dependencies._internal.AndroidLibraryDependencies
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import com.javiersc.hubdle.extensions.kotlin.android.AndroidOptions
import com.javiersc.hubdle.extensions.kotlin.android.library._internal.androidLibraryFeatures
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the

@HubdleDslMarker
public open class KotlinAndroidLibraryExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    EnableableOptions,
    FeaturesOptions<KotlinAndroidLibraryExtension.FeaturesExtension>,
    AndroidOptions,
    RawConfigOptions<KotlinAndroidLibraryExtension.RawConfigExtension>,
    MainAndTestKotlinSourceSetsOptions<AndroidSourceSet>,
    AndroidLibraryDependencies {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.android.library.isEnabled
        set(value) = hubdleState.kotlin.android.library.run { isEnabled = value }

    override val features: FeaturesExtension = objects.newInstance()

    @HubdleDslMarker
    override fun features(action: Action<FeaturesExtension>): Unit = super.features(action)

    override val Project.sourceSets: NamedDomainObjectContainer<out AndroidSourceSet>
        get() = the<LibraryExtension>().sourceSets

    @HubdleDslMarker
    override fun Project.main(action: Action<AndroidSourceSet>) {
        the<LibraryExtension>().sourceSets.named("main", action::execute)
    }

    @HubdleDslMarker
    override fun Project.test(action: Action<AndroidSourceSet>) {
        the<LibraryExtension>().sourceSets.named("test", action::execute)
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.android(action: Action<LibraryExtension>) {
            hubdleState.kotlin.android.library.rawConfig.android = action
        }
    }

    @HubdleDslMarker
    public open class FeaturesExtension {

        @HubdleDslMarker
        public fun Project.coroutines(enabled: Boolean = true) {
            androidLibraryFeatures.coroutines = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedStdlib(enabled: Boolean = true) {
            androidLibraryFeatures.extendedStdlib = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedTesting(enabled: Boolean = true) {
            androidLibraryFeatures.extendedTesting = enabled
        }

        @HubdleDslMarker
        public fun Project.serialization(enabled: Boolean = true, useJson: Boolean = true) {
            androidLibraryFeatures.serialization.isEnabled = enabled
            androidLibraryFeatures.serialization.useJson = useJson
        }
    }
}
