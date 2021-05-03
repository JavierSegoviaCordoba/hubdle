
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            setUrl("https://oss.sonatype.org/content/repositories/snapshots")
            content { includeGroup("com.javiersc.massive-catalogs") }
        }
    }

    versionCatalogs { create("pluginLibs") { from(files("../gradle/libs.versions.toml")) } }
}
