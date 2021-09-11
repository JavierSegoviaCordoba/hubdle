
plugins {
    kotlin("multiplatform")
}

kotlin {
    sourceSets {
        all {
            defaultLanguageSettings
            kotlin.srcDirs("$name/kotlin")
            resources.srcDirs("$name/resources")
        }
    }
}
