package com.javiersc.hubdle.extensions._internal

import com.javiersc.hubdle.extensions.config.testing.HubdleConfigTestingExtension.Options
import com.javiersc.hubdle.extensions.config.testing.hubdleTesting
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_ANNOTATIONS_COMMON_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_TESTNG_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.forKotlinSetsDependencies
import org.gradle.api.Project

internal fun Project.configureDependencies() {
    forKotlinSetsDependencies("test", "commonTest") {
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
