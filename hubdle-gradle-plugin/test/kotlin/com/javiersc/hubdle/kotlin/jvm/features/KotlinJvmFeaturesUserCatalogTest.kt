package com.javiersc.hubdle.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

internal class KotlinJvmFeaturesUserCatalogTest : GradleTest() {

    @Test
    fun `user catalog coroutines`() {
        gradleTestKitTest("kotlin/jvm/features/user-catalog-coroutines") {
            gradlewArgumentFromTXT()
                .outputTrimmed
                .shouldContain(
                    """
                        implementation - Implementation only dependencies for compilation 'main' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1 (n)
                        \--- com.javiersc.kotlin:kotlin-stdlib:0.1.0-alpha.5 (n)
                    """.trimIndent()
                )
                .shouldContain(
                    """
                        testImplementation - Implementation only dependencies for compilation 'test' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlin:kotlin-test:1.6.21 (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1 (n)
                        +--- io.kotest:kotest-assertions-core:5.3.2 (n)
                        \--- org.jetbrains.kotlin:kotlin-test:1.6.21 (n)
                    """.trimIndent()
                )
        }
    }
}
