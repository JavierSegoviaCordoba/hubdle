
plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(30)

    defaultConfig { minSdkVersion(21) }

    sourceSets.all { manifest.srcFile("android${name.capitalize()}/AndroidManifest.xml") }
}
