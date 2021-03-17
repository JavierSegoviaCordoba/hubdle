
dependencyResolutionManagement {
    repositories {
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots") {
            content {
                includeGroup("com.javiersc.massive-catalogs")
            }
        }
    }

    versionCatalogs {
        create("libs") { from("com.javiersc.massive-catalogs:libs-catalog:0.2.0-SNAPSHOT") }
        create("pluginLibs") { from("com.javiersc.massive-catalogs:plugins-catalog:0.2.0-SNAPSHOT") }
    }
}
