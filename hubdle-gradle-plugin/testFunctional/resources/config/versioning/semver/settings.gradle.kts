@file:Suppress("PackageDirectoryMismatch")

plugins { //
    id("com.javiersc.hubdle")
}

dependencyResolutionManagement {
    repositories { //
        mavenLocalTest()
    }
}
