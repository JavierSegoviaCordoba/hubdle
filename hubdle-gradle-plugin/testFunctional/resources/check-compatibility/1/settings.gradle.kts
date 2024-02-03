@file:Suppress("PackageDirectoryMismatch")

rootProject.name = "sandbox-project"

plugins { //
    id("com.javiersc.hubdle")
}

dependencyResolutionManagement {
    repositories { //
        mavenLocalTest()
    }
}
