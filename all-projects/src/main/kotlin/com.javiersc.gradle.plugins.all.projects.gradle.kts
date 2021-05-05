import com.javiersc.plugins.core.groupId

allprojects {
    group = groupId
}

tasks {
    if (findByName("clean") == null) {
        register<Delete>("clean") { delete(files(rootProject.buildDir)) }
    } else {
        getByName<Delete>("clean") { delete(files(rootProject.buildDir)) }
    }

    withType<Test> {
        maxParallelForks = Runtime.getRuntime().availableProcessors()
        useJUnitPlatform()
        useTestNG()
    }
}
