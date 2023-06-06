@file:Suppress("NestedLambdaShadowedImplicitParameter", "FunctionName")

package com.javiersc.hubdle.project

import com.javiersc.gradle.project.test.extensions.GradleProjectTest
import com.javiersc.hubdle.project.extensions.config.publishing.maven.HubdleConfigPublishingMavenPomExtension
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_7
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class HubdleProjectPluginTest : GradleProjectTest() {

    @Test
    fun `given local+properties with multiple properties, when hubdle is applied, then properties are assigned`() {
        hubdle(sandboxPath = "properties") {
            group shouldBe "com.javiersc.gradle"
            the<HubdleConfigPublishingMavenPomExtension>().name.get() shouldBe "Gradle"
        }
    }

    @Test
    fun `given project with Kotlin versions, when project has been evaluated, then kotlin versions are correct`() {
        hubdle(
            config = {
                it.kotlin {
                    it.jvm()
                    it.compilerOptions {
                        it.apiVersion(KOTLIN_1_7)
                        it.languageVersion(KOTLIN_1_9)
                    }
                }
            },
            test = {
                val kotlinCompile = tasks.findByName("compileKotlin") as KotlinCompile
                kotlinCompile.compilerOptions.apiVersion.get() shouldBe KOTLIN_1_7
                kotlinCompile.compilerOptions.languageVersion.get() shouldBe KOTLIN_1_9
            },
        )
    }
}
