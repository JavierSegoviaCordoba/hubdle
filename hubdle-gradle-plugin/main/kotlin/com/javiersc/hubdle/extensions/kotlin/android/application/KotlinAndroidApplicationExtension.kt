package com.javiersc.hubdle.extensions.kotlin.android.application

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.api.dsl.ApplicationExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import com.javiersc.hubdle.extensions.kotlin.android.AndroidOptions
import com.javiersc.hubdle.extensions.kotlin.android.application._internal.androidApplicationFeatures
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
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class KotlinAndroidApplicationExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    EnableableOptions,
    FeaturesOptions<KotlinAndroidApplicationExtension.FeaturesExtension>,
    AndroidOptions,
    RawConfigOptions<KotlinAndroidApplicationExtension.RawConfigExtension>,
    MainAndTestKotlinSourceSetsOptions<KotlinSourceSet> {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.android.application.isEnabled
        set(value) = hubdleState.kotlin.android.application.run { isEnabled = value }

    override val features: FeaturesExtension = objects.newInstance()

    @HubdleDslMarker
    override fun features(action: Action<FeaturesExtension>): Unit = super.features(action)

    public var Project.applicationId: String?
        get() = hubdleState.kotlin.android.application.applicationId
        set(value) = hubdleState.kotlin.android.application.run { applicationId = value }

    public var Project.namespace: String?
        get() = hubdleState.kotlin.android.namespace
        set(value) = hubdleState.kotlin.run { android.namespace = value }

    public var Project.versionCode: Int?
        get() = hubdleState.kotlin.android.application.versionCode
        set(value) = hubdleState.kotlin.android.application.run { versionCode = value }

    public var Project.versionName: String?
        get() = hubdleState.kotlin.android.application.versionName
        set(value) = hubdleState.kotlin.android.application.run { versionName = value }

    override val Project.sourceSets: NamedDomainObjectContainer<out AndroidSourceSet>
        get() = the<ApplicationExtension>().sourceSets

    @HubdleDslMarker
    override fun Project.main(action: Action<KotlinSourceSet>) {
        the<KotlinProjectExtension>().sourceSets.named("main", action::execute)
    }

    @HubdleDslMarker
    override fun Project.test(action: Action<KotlinSourceSet>) {
        the<KotlinProjectExtension>().sourceSets.named("test", action::execute)
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.android(action: Action<ApplicationExtension>) {
            hubdleState.kotlin.android.application.rawConfig.android = action
        }
    }

    @HubdleDslMarker
    public open class FeaturesExtension {

        @HubdleDslMarker
        public fun Project.coroutines(enabled: Boolean = true) {
            androidApplicationFeatures.coroutines = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedStdlib(enabled: Boolean = true) {
            androidApplicationFeatures.extendedStdlib = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedTesting(enabled: Boolean = true) {
            androidApplicationFeatures.extendedTesting = enabled
        }

        @HubdleDslMarker
        public fun Project.serialization(enabled: Boolean = true, useJson: Boolean = true) {
            androidApplicationFeatures.serialization.isEnabled = enabled
            androidApplicationFeatures.serialization.useJson = useJson
        }
    }
}
