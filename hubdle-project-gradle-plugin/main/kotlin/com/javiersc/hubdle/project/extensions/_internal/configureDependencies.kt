package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.config.testing.HubdleConfigTestingExtension.Options
import com.javiersc.hubdle.project.extensions.config.testing.hubdleTesting
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_ANNOTATIONS_COMMON_MODULE
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT_MODULE
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_TESTNG_MODULE
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies

internal fun HubdleConfigurableExtension.configurableDependencies() {
    configurable {
        project.forKotlinSetsDependencies(
            TEST,
            COMMON_TEST,
            TEST_FUNCTIONAL,
            TEST_INTEGRATION,
        ) {
            implementation(catalogDependency(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE))
            implementation(
                catalogDependency(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_ANNOTATIONS_COMMON_MODULE)
            )

            if (hubdleTesting.isFullEnabled.get()) {
                val module =
                    when (hubdleTesting.options.get()) {
                        Options.JUnit -> ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT_MODULE
                        Options.JUnitPlatform -> ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE
                        Options.TestNG -> ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_TESTNG_MODULE
                        else -> ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE
                    }
                implementation(catalogDependency(module))
            }
        }
    }
}
