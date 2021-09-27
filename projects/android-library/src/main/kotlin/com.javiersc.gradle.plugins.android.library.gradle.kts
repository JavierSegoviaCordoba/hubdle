import com.android.build.gradle.LibraryExtension
import com.javiersc.plugins.android.core.AndroidSdk
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
    }
}

configure<LibraryExtension> {
    compileSdk = AndroidSdk.compileSdk

    defaultConfig { minSdk = AndroidSdk.minSdk }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    sourceSets.all {
        assets.srcDirs("$name/assets")
        java.srcDirs("$name/java", "$name/kotlin")
        manifest.srcFile("$name/AndroidManifest.xml")
        res.srcDirs("$name/res")
        resources.srcDirs("$name/resources")
    }
}
