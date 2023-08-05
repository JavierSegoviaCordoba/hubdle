package com.javiersc.hubdle.project.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.hubdle.hubdle.gradle.plugin.HubdleGradlePluginProjectData
import com.javiersc.hubdle.project.fixtures.KotlinVersionEnv
import com.javiersc.hubdle.project.fixtures.KotlinVersionRegexOrEmptyOrNull
import io.kotest.matchers.string.shouldContain
import java.io.File
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS

internal class KotlinJvmMoleculeFeatureTest : GradleTestKitTest() {

    @Test
    @EnabledOnOs(value = [OS.LINUX, OS.MAC])
    @EnabledIfSystemProperty(named = KotlinVersionEnv, matches = KotlinVersionRegexOrEmptyOrNull)
    fun `molecule feature`() {
        gradleTestKitTest("kotlin/jvm/features/molecule") {
            val hubdleTomlDestination =
                projectDir.resolve("gradle/hubdle.libs.versions.toml").apply {
                    parentFile.mkdirs()
                    createNewFile()
                }
            File(HubdleGradlePluginProjectData.RootDirAbsolutePath)
                .resolve("gradle/hubdle.libs.versions.toml")
                .copyTo(target = hubdleTomlDestination, overwrite = true)
            withArguments("run").build().outputTrimmed.apply {
                shouldContain("FooState(name=Unknown")
                shouldContain("FooState(name=B")
            }
        }
    }
}
