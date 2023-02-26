package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.project.extensions._internal.COMMON_TEST
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.TEST
import com.javiersc.hubdle.project.extensions._internal.TEST_FIXTURES
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.project.extensions._internal.TEST_INTEGRATION
import com.javiersc.hubdle.project.extensions._internal.catalogDependency
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE as JAVIER_SC_KOTLIN_JUNIT5_MODULE
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT_MODULE
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_TEST_TESTNG_MODULE
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlinAny
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.junit.JUnitOptions
import org.gradle.api.tasks.testing.junitplatform.JUnitPlatformOptions
import org.gradle.api.tasks.testing.testng.TestNGOptions
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

public open class HubdleKotlinExtendedStdlibFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = hubdleKotlinAny

    override val priority: Priority = Priority.P4

    override fun Project.defaultConfiguration() {
        configurable {
            forKotlinSetsDependencies(MAIN, COMMON_MAIN) {
                implementation(catalogDependency(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE))
            }

            forKotlinSetsDependencies(
                TEST,
                COMMON_TEST,
                TEST_FUNCTIONAL,
                TEST_INTEGRATION,
                TEST_FIXTURES,
            ) {
                implementation(calculateJavierScKotlinTestDependency())
            }
        }
    }
}

public interface HubdleKotlinExtendedStdlibDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val extendedStdlib: HubdleKotlinExtendedStdlibFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun extendedStdlib(
        action: Action<HubdleKotlinExtendedStdlibFeatureExtension> = Action {}
    ) {
        extendedStdlib.enableAndExecute(action)
    }
}

private fun KotlinDependencyHandler.calculateJavierScKotlinTestDependency():
    Provider<MinimalExternalModuleDependency> =
    when (project.tasks.withType<Test>().firstOrNull()?.options) {
        is JUnitOptions -> catalogDependency(COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT_MODULE)
        is JUnitPlatformOptions -> catalogDependency(JAVIER_SC_KOTLIN_JUNIT5_MODULE)
        is TestNGOptions -> catalogDependency(COM_JAVIERSC_KOTLIN_KOTLIN_TEST_TESTNG_MODULE)
        else -> catalogDependency(COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT_MODULE)
    }

internal val HubdleEnableableExtension.hubdleExtendedStdlibFeature:
    HubdleKotlinCoroutinesFeatureExtension
    get() = getHubdleExtension()
