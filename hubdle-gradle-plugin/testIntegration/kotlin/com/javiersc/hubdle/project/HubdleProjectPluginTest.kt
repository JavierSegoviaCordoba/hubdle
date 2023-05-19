package com.javiersc.hubdle.project

import com.javiersc.gradle.project.test.extensions.GradleProjectTest
import com.javiersc.hubdle.project._internal.hubdle
import com.javiersc.hubdle.project.extensions.config.publishing.maven.HubdleConfigPublishingMavenPomExtension
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.gradle.kotlin.dsl.the

class HubdleProjectPluginTest : GradleProjectTest() {

    @Test
    fun `given local+properties with multiple properties, when hubdle is applied, then properties are assigned`() {
        hubdle("properties") {
            group shouldBe "com.javiersc.local"
            the<HubdleConfigPublishingMavenPomExtension>().name.get() shouldBe "Local"
        }
    }
}
