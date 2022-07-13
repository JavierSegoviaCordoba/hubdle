import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `javiersc-versioning`
    `javiersc-all-projects`
    `javiersc-changelog`
    `javiersc-code-analysis`
    `javiersc-code-coverage`
    `javiersc-code-formatter`
    `javiersc-docs`
    `kotlinx-binary-compatibility-validator`
    `javiersc-nexus`
    `javiersc-readme-badges-generator`
    `build-itself` // TODO: keep with Hubdle migration
}

// TODO: remove in Hubdle migration
allprojects {
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

apiValidation {
    ignoredPackages.add("com.javiersc.hubdle.extensions.dependencies._internal")
    ignoredClasses.add("Hubdle_dependenciesKt")
}
