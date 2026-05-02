package com.javiersc.hubdle.declarative

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.declarative.platform.HubdleProperties
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

class HubdleDeclarativeTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle is applied AND logging is enabled THEN hubdle declarative logs are printed`() {
        gradleTestKitTest("hubdle") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContain("Hubdle enabled on :")
        }
    }
}
