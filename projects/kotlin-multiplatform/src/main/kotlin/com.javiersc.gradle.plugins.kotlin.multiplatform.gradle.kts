import com.javiersc.plugins.core.AndroidSdkVersion

plugins {
    kotlin("multiplatform")
    id("com.android.library")
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

android {
    compileSdkVersion(AndroidSdkVersion.compileSdkVersion)

    defaultConfig { minSdkVersion(AndroidSdkVersion.minSdkVersion) }

    sourceSets.all {
        manifest.srcFile("android${name.capitalize()}/AndroidManifest.xml")
    }
}
