package com.javiersc.hubdle.project

import com.javiersc.gradle.project.test.extensions.GradleProjectTest
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions.HubdleExtension
import hubdle
import org.gradle.api.Project

@HubdleDslMarker
internal fun GradleProjectTest.hubdle(
    sandboxPath: String? = null,
    config: (hubdle: HubdleExtension) -> Unit = {},
    test: Project.() -> Unit,
) {
    gradleProjectTest(sandboxPath) {
        pluginManager.apply("com.javiersc.hubdle")
        hubdle(config)
        test()
    }
}
