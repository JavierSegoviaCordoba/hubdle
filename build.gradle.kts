plugins {
    versioning
    `all-projects`
    changelog
    `code-analysis`
    `dependency-updates`
    docs
    `binary-compatibility-validator`
    nexus
    `readme-badges`
}

tasks {
    withType<Test> {
        maxParallelForks = Runtime.getRuntime().availableProcessors()
        useJUnitPlatform()
        useTestNG()
    }
}
