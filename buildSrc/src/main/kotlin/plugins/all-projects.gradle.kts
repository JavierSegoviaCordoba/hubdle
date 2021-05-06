import internal.groupId

allprojects {
    group = groupId
}

tasks {
    if (findByName("clean") == null) register("clean")

    named("clean") { delete(files(rootProject.buildDir)) }

    withType<Test> {
        maxParallelForks = Runtime.getRuntime().availableProcessors()
        useJUnitPlatform()
        useTestNG()
    }
}
