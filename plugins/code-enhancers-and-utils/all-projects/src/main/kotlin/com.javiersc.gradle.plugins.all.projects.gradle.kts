@file:Suppress("MagicNumber")

import com.javiersc.gradle.plugins.all.projects.groupId

plugins.apply(LifecycleBasePlugin::class)

allprojects {
    group = groupId

    pluginManager.apply("com.adarshr.test-logger")

    tasks {
        withType<Test> {
            testLogging.showStandardStreams = true
            maxParallelForks =
                (Runtime.getRuntime().availableProcessors() / 3).takeIf { it > 0 } ?: 1
            useJUnitPlatform()
        }
    }
}
