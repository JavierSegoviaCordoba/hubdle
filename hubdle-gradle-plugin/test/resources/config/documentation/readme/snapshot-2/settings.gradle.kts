rootProject.name = providers.gradleProperty("root.project.name").forUseAtConfigurationTime().get()

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":example-core")
