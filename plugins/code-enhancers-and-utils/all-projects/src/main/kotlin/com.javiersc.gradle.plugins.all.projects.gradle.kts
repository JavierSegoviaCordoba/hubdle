import com.javiersc.gradle.plugins.all.projects.groupId

plugins.apply(LifecycleBasePlugin::class)

allprojects {
    group = groupId

    pluginManager.apply("com.adarshr.test-logger")

    tasks {
        withType<Test> {
            maxParallelForks = Runtime.getRuntime().availableProcessors()
            useJUnitPlatform()
            useTestNG()
        }
    }
}
