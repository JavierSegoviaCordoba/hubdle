package com.javiersc.hubdle.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.test.extensions.gradlewArgumentFromTXT
import com.javiersc.gradle.testkit.test.extensions.outputTrimmed
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

internal class KotlinJvmFeaturesTest {

    private val basePath = "kotlin/jvm/features"

    @Test
    fun `catalog disable javiersc stdlib`() {
        gradleTestKitTest("$basePath/catalog-disable-javiersc-stdlib", isolated = true) {
            gradlewArgumentFromTXT()
                .outputTrimmed.shouldNotContain("com.javiersc.kotlin:kotlin-stdlib")
        }
    }

    @Test
    fun `hubdle catalog coroutines`() {
        gradleTestKitTest("$basePath/hubdle-catalog-coroutines", isolated = true) {
            gradlewArgumentFromTXT()
                .outputTrimmed
                .shouldContain(
                    """
                        implementation - Implementation only dependencies for compilation 'main' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3 (n)
                        \--- com.javiersc.kotlin:kotlin-stdlib:0.1.0-alpha.5 (n)
                    """.trimIndent()
                )
                .shouldContain(
                    """
                        testImplementation - Implementation only dependencies for compilation 'test' (target  (jvm)). (n)
                        +--- org.jetbrains.kotlin:kotlin-test:1.6.21 (n)
                        +--- org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.3 (n)
                        +--- io.kotest:kotest-assertions-core:5.3.2 (n)
                        \--- org.jetbrains.kotlin:kotlin-test:1.6.21 (n)
                    """.trimIndent()
                )
        }
    }

    @Test
    fun `user catalog coroutines`() {
        gradleTestKitTest("$basePath/user-catalog-coroutines", isolated = true) {
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
