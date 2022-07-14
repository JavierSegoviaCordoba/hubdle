@file:Suppress("MaxLineLength")

package com.javiersc.hubdle.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_VERSION
import com.javiersc.hubdle.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_VERSION
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_VERSION
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

internal class KotlinJvmFeaturesUserCatalogTest : GradleTest() {

    @Test
    fun `user catalog coroutines`() {
        gradleTestKitTest("kotlin/jvm/features/user-catalog-coroutines") {
            gradlew("assemble")
            gradlewArgumentFromTXT()
                .outputTrimmed
                .shouldContain(
                    """
                        implementation - Implementation only dependencies for compilation 'main' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1 (n)
                        \--- com.javiersc.kotlin:kotlin-stdlib:$COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_VERSION (n)
                    """.trimIndent()
                )
                .shouldContain(
                    """
                        testImplementation - Implementation only dependencies for compilation 'test' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlin:kotlin-test:1.6.10 (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1 (n)
                        +--- io.kotest:kotest-assertions-core:5.3.0 (n)
                        \--- org.jetbrains.kotlin:kotlin-test:1.6.10 (n)
                    """.trimIndent()
                )
        }
    }
}
