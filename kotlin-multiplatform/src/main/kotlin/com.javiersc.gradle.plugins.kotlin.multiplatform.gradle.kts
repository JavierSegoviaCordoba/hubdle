
plugins {
    kotlin("multiplatform")
}

kotlin {
    sourceSets {
        all {
            defaultLanguageSettings
            kotlin.srcDirs(name)
            resources.srcDirs(name)
        }
    }
}
