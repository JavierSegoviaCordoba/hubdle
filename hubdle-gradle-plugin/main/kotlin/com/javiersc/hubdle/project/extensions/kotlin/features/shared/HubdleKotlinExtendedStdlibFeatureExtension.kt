package com.javiersc.hubdle.project.extensions.kotlin.features.shared

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.JVM_TEST
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.TEST
import com.javiersc.hubdle.project.extensions._internal.TEST_FIXTURES
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.project.extensions._internal.TEST_INTEGRATION
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.library
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_kotlin_kotlinStdlib
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_kotlin_kotlinTestJunit
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_kotlin_kotlinTestJunit5
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.javiersc_kotlin_kotlinTestNG
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
                implementation(library(javiersc_kotlin_kotlinStdlib))
            }

            forKotlinSetsDependencies(
                TEST,
                JVM_TEST,
                TEST_FUNCTIONAL,
                TEST_INTEGRATION,
                TEST_FIXTURES,
            ) {
                implementation(calculateJavierScKotlinTestJvmDependency())
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

private fun Project.calculateJavierScKotlinTestJvmDependency():
    Provider<MinimalExternalModuleDependency?> =
    when (tasks.withType<Test>().firstOrNull()?.options) {
        is JUnitOptions -> library(javiersc_kotlin_kotlinTestJunit)
        is JUnitPlatformOptions -> library(javiersc_kotlin_kotlinTestJunit5)
        is TestNGOptions -> library(javiersc_kotlin_kotlinTestNG)
        else -> library(javiersc_kotlin_kotlinTestJunit)
    }

internal val HubdleEnableableExtension.hubdleExtendedStdlibFeature:
    HubdleKotlinCoroutinesFeatureExtension
    get() = getHubdleExtension()
