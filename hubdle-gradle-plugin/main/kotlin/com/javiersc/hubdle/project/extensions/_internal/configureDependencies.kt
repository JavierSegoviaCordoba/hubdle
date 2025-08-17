package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.config.testing.HubdleConfigTestingExtension.Options
import com.javiersc.hubdle.project.extensions.config.testing.hubdleTesting
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_test
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_test_annotations_common
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_test_junit
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_test_junit5
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_test_testng
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider

internal fun HubdleConfigurableExtension.configurableDependencies() {
    lazyConfigurable {
        project.forKotlinSetsDependencies(
            TEST,
            COMMON_TEST,
            TEST_FIXTURES,
            TEST_FUNCTIONAL,
            TEST_INTEGRATION,
        ) {
            implementation(library(jetbrains_kotlin_test))
            implementation(library(jetbrains_kotlin_test_annotations_common))
        }

        project.forKotlinSetsDependencies(
            TEST,
            JVM_TEST,
            TEST_FIXTURES,
            TEST_FUNCTIONAL,
            TEST_INTEGRATION,
        ) {
            if (hubdleTesting.isFullEnabled.get()) {
                val testModuleFramework: Provider<MinimalExternalModuleDependency> = provider {
                    when (hubdleTesting.options.get()) {
                        Options.JUnit -> library(jetbrains_kotlin_test_junit)
                        Options.JUnitPlatform -> library(jetbrains_kotlin_test_junit5)
                        Options.TestNG -> library(jetbrains_kotlin_test_testng)
                        else -> library(jetbrains_kotlin_test_junit)
                    }.get()
                }
                implementation(testModuleFramework)
            }
        }
    }
}
