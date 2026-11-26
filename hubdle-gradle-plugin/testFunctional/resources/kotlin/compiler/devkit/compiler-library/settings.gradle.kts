@file:Suppress("PackageDirectoryMismatch")

plugins { //
    id("com.javiersc.hubdle")
}

dependencyResolutionManagement {
    repositories { //
        mavenLocalTest()
    }
}

gradle.projectsLoaded {
    val repositories = settings.dependencyResolutionManagement.repositories
    for (repository in repositories.withType<MavenArtifactRepository>()) {
        println("REPOSITORY ${repository.url}")
    }
}
