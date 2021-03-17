plugins {
    versioning
    changelog
    `code-analysis`
    `dependency-updates`
    docs
    `binary-compatibility-validator`
    nexus
    readme
}

tasks {
    withType<Test> {
        maxParallelForks = Runtime.getRuntime().availableProcessors()
        useJUnitPlatform()
        useTestNG()
    }
}
