import com.javiersc.plugins.core.AndroidSdkVersion

plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(AndroidSdkVersion.compileSdkVersion)

    defaultConfig { minSdkVersion(AndroidSdkVersion.minSdkVersion) }

    sourceSets.all {
        assets.srcDirs("$name/assets")
        java.srcDirs("$name/java", "$name/kotlin")
        manifest.srcFile("$name/AndroidManifest.xml")
        res.srcDirs("$name/res")
        resources.srcDirs("$name/resources")
    }
}
