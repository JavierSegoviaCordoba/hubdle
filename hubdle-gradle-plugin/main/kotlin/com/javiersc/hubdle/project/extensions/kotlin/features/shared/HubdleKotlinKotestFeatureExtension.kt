package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.COMMON_TEST
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
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.kotest_assertions_core
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property

public open class HubdleKotlinKotestFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override fun Project.defaultConfiguration() {
        configurable {
            forKotlinSetsDependencies(
                TEST,
                COMMON_TEST,
                TEST_FUNCTIONAL,
                TEST_INTEGRATION,
                TEST_FIXTURES,
            ) {
                implementation(library(kotest_assertions_core))
            }
        }
    }
}

public interface HubdleKotlinKotestDelegateFeatureExtension : BaseHubdleExtension {

    public val kotest: HubdleKotlinKotestFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun kotest(action: Action<HubdleKotlinKotestFeatureExtension> = Action {}) {
        kotest.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleKotestFeature: HubdleKotlinKotestFeatureExtension
    get() = getHubdleExtension()
