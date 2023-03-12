@file:Suppress("MaxLineLength")

package com.javiersc.hubdle.project.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.hubdle.project.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_VERSION
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

internal class KotlinJvmFeaturesUserCatalogTest : GradleTestKitTest() {

    @Test
    fun `user catalog coroutines`() {
        gradleTestKitTest("kotlin/jvm/features/user-catalog-coroutines") {
            gradlew("assemble")
            val output = gradlewArgumentFromTXT().outputTrimmed
            output
                .shouldContain(
                    """
                        implementation - Implementation only dependencies for compilation 'main' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1 (n)
                        \--- com.javiersc.kotlin:kotlin-stdlib:$COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_VERSION (n)
                    """
                        .trimIndent()
                )
                .shouldContain(
                    """
                        testImplementation - Implementation only dependencies for compilation 'test' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlin:kotlin-test:1.6.10 (n)
                        +--- org.jetbrains.kotlin:kotlin-test-annotations-common:1.6.10 (n)
                        +--- org.jetbrains.kotlin:kotlin-test-junit5:1.6.10 (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1 (n)
                        +--- com.javiersc.kotlin:kotlin-test-junit5:0.1.0-alpha.12 (n)
                        \--- io.kotest:kotest-assertions-core:5.3.0 (n)
                    """
                        .trimIndent()
                )
        }
    }
}
