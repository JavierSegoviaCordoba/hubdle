
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") { from("com.javiersc.massive-catalogs:libs-catalog:0.1.0-alpha.3") }
        create("pluginLibs") { from("com.javiersc.massive-catalogs:plugins-catalog:0.1.0-alpha.3") }
    }
}
