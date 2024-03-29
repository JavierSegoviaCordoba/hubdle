@file:Suppress("PackageDirectoryMismatch")

plugins { //
    id("com.javiersc.hubdle")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven(url = rootProject.projectDir.resolve("build/mavenLocalBuildTest/repository").toURI())
        mavenLocalTest()
    }
}
