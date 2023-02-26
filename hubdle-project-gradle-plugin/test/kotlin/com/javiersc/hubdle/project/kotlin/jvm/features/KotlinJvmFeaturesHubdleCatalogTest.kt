@file:Suppress("MaxLineLength")

package com.javiersc.hubdle.project.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_VERSION
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT5_VERSION
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_VERSION
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_VERSION
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_ANNOTATIONS_COMMON_VERSION
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT5_VERSION
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_VERSION
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import org.junit.jupiter.api.Test

internal class KotlinJvmFeaturesHubdleCatalogTest : GradleTestKitTest() {

    @Test
    fun `catalog disable javiersc stdlib`() {
        gradleTestKitTest("kotlin/jvm/features/catalog-disable-javiersc-stdlib") {
            gradlew("assemble")
            gradlewArgumentFromTXT()
                .outputTrimmed
                .shouldNotContain("com.javiersc.kotlin:kotlin-stdlib")
        }
    }

    @Test
    fun `hubdle catalog coroutines`() {
        gradleTestKitTest("kotlin/jvm/features/hubdle-catalog-coroutines") {
            gradlew("assemble")
            val output = gradlewArgumentFromTXT().outputTrimmed
            output
                .shouldContain(
                    """
                        implementation - Implementation only dependencies for compilation 'main' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:$ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_VERSION (n)
                        \--- com.javiersc.kotlin:kotlin-stdlib:$COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_VERSION (n)
                    """
                        .trimIndent()
                )
                .shouldContain(
                    """
                        testImplementation - Implementation only dependencies for compilation 'test' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlin:kotlin-test:$ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_VERSION (n)
                        +--- org.jetbrains.kotlin:kotlin-test-annotations-common:$ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_ANNOTATIONS_COMMON_VERSION (n)
                        +--- org.jetbrains.kotlin:kotlin-test-junit5:$ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT5_VERSION (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-test:$ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_VERSION (n)
                        +--- com.javiersc.kotlin:kotlin-test-junit5:$COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT5_VERSION (n)
                        \--- io.kotest:kotest-assertions-core:$IO_KOTEST_KOTEST_ASSERTIONS_CORE_VERSION (n)
                    """
                        .trimIndent()
                )
        }
    }
}
