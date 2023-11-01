package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ANDROID_MAIN
import com.javiersc.hubdle.project.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.project.extensions._internal.COMMON_TEST
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.TEST
import com.javiersc.hubdle.project.extensions._internal.TEST_FIXTURES
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.project.extensions._internal.TEST_INTEGRATION
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlinx_kotlinxCoroutinesAndroid
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlinx_kotlinxCoroutinesCore
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlinx_kotlinxCoroutinesTest
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.project.extensions.kotlin.android.hubdleAndroidAny
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.targets.hubdleKotlinMultiplatformAndroid
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

public open class HubdleKotlinCoroutinesFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val priority: Priority = Priority.P4

    override fun Project.defaultConfiguration() {
        configurable {
            configure<KotlinProjectExtension> {
                forKotlinSetsDependencies(MAIN, COMMON_MAIN) {
                    implementation(library(jetbrains_kotlinx_kotlinxCoroutinesCore))
                }

                forKotlinSetsDependencies(
                    TEST,
                    COMMON_TEST,
                    TEST_FUNCTIONAL,
                    TEST_INTEGRATION,
                    TEST_FIXTURES
                ) {
                    implementation(library(jetbrains_kotlinx_kotlinxCoroutinesTest))
                }
            }
        }
        configurable(isEnabled = isAnyAndroidFullEnabled) {
            forKotlinSetsDependencies(MAIN, ANDROID_MAIN) {
                implementation(library(jetbrains_kotlinx_kotlinxCoroutinesAndroid))
            }
        }
    }
}

private val HubdleKotlinCoroutinesFeatureExtension.isAnyAndroidFullEnabled: Property<Boolean>
    get() = property {
        isFullEnabled.get() &&
            (hubdleAndroidAny.any { it.isFullEnabled.get() } ||
                hubdleKotlinMultiplatformAndroid.isFullEnabled.get())
    }

public interface HubdleKotlinCoroutinesDelegateFeatureExtension : BaseHubdleExtension {

    public val coroutines: HubdleKotlinCoroutinesFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun coroutines(action: Action<HubdleKotlinCoroutinesFeatureExtension> = Action {}) {
        coroutines.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleCoroutinesFeature:
    HubdleKotlinCoroutinesFeatureExtension
    get() = getHubdleExtension()
