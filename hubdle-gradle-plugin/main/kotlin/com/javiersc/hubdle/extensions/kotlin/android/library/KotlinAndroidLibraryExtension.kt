package com.javiersc.hubdle.extensions.kotlin.android.library

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import com.javiersc.hubdle.extensions.kotlin.android.AndroidOptions
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
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
    MainAndTestKotlinSourceSetsOptions<AndroidSourceSet> {

    override var isEnabled: Boolean = IS_ENABLED

    override val features: FeaturesExtension = objects.newInstance()

    @HubdleDslMarker
    override fun features(action: Action<FeaturesExtension>): Unit = super.features(action)

    override var compileSdk: Int = AndroidOptions.COMPILE_SDK

    override var minSdk: Int = AndroidOptions.MIN_SDK

    override var jvmVersion: Int = KotlinJvmOptions.JVM_VERSION

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

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { action.execute(the()) }
    }

    public open class RawConfigExtension {
        public fun Project.android(action: Action<LibraryExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }
    }

    @HubdleDslMarker
    public open class FeaturesExtension {

        private val Project.androidFeatures
            get() = hubdleState.kotlin.android.features

        @HubdleDslMarker
        public fun Project.coroutines(enable: Boolean = COROUTINES) {
            androidFeatures.coroutines = enable
        }

        public var javierScStdlib: Boolean = JAVIER_SC_STDLIB

        @HubdleDslMarker
        public fun Project.javierScStdlib(enable: Boolean = JAVIER_SC_STDLIB) {
            androidFeatures.javierScStdlib = enable
        }

        public companion object {
            internal const val COROUTINES = false
            internal const val JAVIER_SC_STDLIB = true
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
