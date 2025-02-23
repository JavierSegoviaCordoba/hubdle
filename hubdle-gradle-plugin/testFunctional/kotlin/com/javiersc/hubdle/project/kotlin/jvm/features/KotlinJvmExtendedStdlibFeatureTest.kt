package com.javiersc.hubdle.project.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.hubdle.hubdle.gradle.plugin.HubdleGradlePluginProjectData
import java.io.File
import org.junit.jupiter.api.Test

internal class KotlinJvmExtendedStdlibFeatureTest : GradleTestKitTest() {

    @Test
    fun `extended stdlib feature`() {
        gradleTestKitTest("kotlin/jvm/features/extended-stdlib") {
            val hubdleTomlDestination =
                projectDir.resolve("gradle/hubdle.libs.versions.toml").apply {
                    parentFile.mkdirs()
                    createNewFile()
                }
            File(HubdleGradlePluginProjectData.RootDirAbsolutePath)
                .resolve("gradle/hubdle.libs.versions.toml")
                .copyTo(target = hubdleTomlDestination, overwrite = true)
            withArguments("build", "--no-scan").build().outputTrimmed
        }
    }
}
