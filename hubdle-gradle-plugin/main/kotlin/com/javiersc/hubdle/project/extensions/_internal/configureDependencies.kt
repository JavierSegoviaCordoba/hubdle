package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.config.testing.HubdleConfigTestingExtension.Options
import com.javiersc.hubdle.project.extensions.config.testing.hubdleTesting
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinTest
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinTestAnnotationsCommon
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinTestJUnit
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinTestJUnit5
import com.javiersc.hubdle.project.extensions.dependencies._internal.aliases.jetbrains_kotlin_kotlinTestNG
import com.javiersc.hubdle.project.extensions.kotlin._internal.forKotlinSetsDependencies
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider

internal fun HubdleConfigurableExtension.configurableDependencies() {
    configurable {
        project.forKotlinSetsDependencies(
            TEST,
            COMMON_TEST,
            TEST_FUNCTIONAL,
            TEST_INTEGRATION,
        ) {
            implementation(library(jetbrains_kotlin_kotlinTest))
            implementation(library(jetbrains_kotlin_kotlinTestAnnotationsCommon))
        }

        project.forKotlinSetsDependencies(
            TEST,
            JVM_TEST,
            TEST_FUNCTIONAL,
            TEST_INTEGRATION,
        ) {
            if (hubdleTesting.isFullEnabled.get()) {
                val testModuleFramework: Provider<MinimalExternalModuleDependency?> = provider {
                    when (hubdleTesting.options.get()) {
                        Options.JUnit -> library(jetbrains_kotlin_kotlinTestJUnit)
                        Options.JUnitPlatform -> library(jetbrains_kotlin_kotlinTestJUnit5)
                        Options.TestNG -> library(jetbrains_kotlin_kotlinTestNG)
                        else -> library(jetbrains_kotlin_kotlinTestJUnit)
                    }.get()
                }
                implementation(testModuleFramework)
            }
        }
    }
}
