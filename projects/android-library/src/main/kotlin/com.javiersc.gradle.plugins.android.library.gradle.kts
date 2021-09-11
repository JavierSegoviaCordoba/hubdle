
plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(31)

    defaultConfig { minSdkVersion(21) }

    sourceSets.all {
        assets.srcDirs("$name/assets")
        java.srcDirs("$name/java", "$name/kotlin")
        manifest.srcFile("$name/AndroidManifest.xml")
        res.srcDirs("$name/res")
        resources.srcDirs("$name/resources")
    }
}
