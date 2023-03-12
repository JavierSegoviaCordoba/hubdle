rootProject.name = "sandbox-project"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven(url = rootProject.projectDir.resolve("build/mavenLocalRandom/repository").toURI())
    }
}
