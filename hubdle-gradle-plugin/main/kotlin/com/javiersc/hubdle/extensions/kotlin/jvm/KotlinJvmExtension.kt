package com.javiersc.hubdle.extensions.kotlin.jvm

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
import com.javiersc.hubdle.extensions.options.GradleDependenciesOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import com.javiersc.hubdle.extensions.options.SourceDirectoriesOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class KotlinJvmExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    EnableableOptions,
    FeaturesOptions<KotlinJvmExtension.FeaturesExtension>,
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    RawConfigOptions<KotlinJvmExtension.RawConfigExtension>,
    MainAndTestKotlinSourceSetsOptions<KotlinSourceSet>,
    GradleDependenciesOptions {

    override var isEnabled: Boolean = IS_ENABLED

    override val features: FeaturesExtension = objects.newInstance()

    @HubdleDslMarker
    override fun features(action: Action<FeaturesExtension>): Unit = super.features(action)

    override var jvmVersion: Int = KotlinJvmOptions.JVM_VERSION

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = the<KotlinJvmProjectExtension>().sourceSets

    @HubdleDslMarker
    override fun Project.main(action: Action<KotlinSourceSet>) {
        the<KotlinJvmProjectExtension>().sourceSets.named("main", action::execute)
    }

    @HubdleDslMarker
    override fun Project.test(action: Action<KotlinSourceSet>) {
        the<KotlinJvmProjectExtension>().sourceSets.named("test", action::execute)
    }

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { action.execute(it.the()) }
    }

    public open class RawConfigExtension {
        public fun Project.kotlin(action: Action<KotlinJvmProjectExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }
    }

    @HubdleDslMarker
    public open class FeaturesExtension {

        private val Project.jvmFeatures
            get() = hubdleState.kotlin.jvm.features

        @HubdleDslMarker
        public fun Project.coroutines(enabled: Boolean = true) {
            jvmFeatures.coroutines = enabled
        }

        @HubdleDslMarker
        public fun Project.javierScGradleExtensions(enabled: Boolean = true) {
            jvmFeatures.javierScGradleExtensions = enabled
        }

        @HubdleDslMarker
        public fun Project.javierScStdlib(enabled: Boolean = true) {
            jvmFeatures.javierScStdlib = enabled
        }

        public companion object {
            internal const val COROUTINES = false
            internal const val JAVIERSC_GRADLE_EXTENSIONS = false
            internal const val JAVIER_SC_STDLIB = true
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
