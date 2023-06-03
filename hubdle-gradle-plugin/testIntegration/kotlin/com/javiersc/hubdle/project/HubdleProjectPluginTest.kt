@file:Suppress("NestedLambdaShadowedImplicitParameter", "FunctionName")

package com.javiersc.hubdle.project

import com.javiersc.gradle.project.test.extensions.GradleProjectTest
import com.javiersc.hubdle.project.extensions.config.publishing.maven.HubdleConfigPublishingMavenPomExtension
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.gradle.kotlin.dsl.the
import org.sonarqube.gradle.SonarExtension

class HubdleProjectPluginTest : GradleProjectTest() {

    @Test
    fun `given local+properties with multiple properties, when hubdle is applied, then properties are assigned`() {
        hubdle(sandboxPath = "properties") {
            group shouldBe "com.javiersc.gradle"
            the<HubdleConfigPublishingMavenPomExtension>().name.get() shouldBe "Gradle"
        }
    }

    @Test
    fun `given project without sonar, when project has been evaluated, then sonar is skipped`() {
        hubdle(
            config = { it.config { it.analysis { it.sonar { it.isEnabled.set(false) } } } },
            test = { afterEvaluate { it.the<SonarExtension>().isSkipProject shouldBe true } },
        )
    }
}
