import internal.groupId

allprojects {
    group = groupId
}

tasks.register<Delete>("clean") {
    delete(files(rootProject.buildDir))
}
