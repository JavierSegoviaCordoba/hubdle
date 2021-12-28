plugins {
    id("com.javiersc.gradle.plugins.projects.version.catalog")
}

allprojects {
    group = "com.example.foo-bar"
    version = "1.2.3"
}

projectsVersionCatalog {
    removeVersionAliasPrefix.set("com")
}
