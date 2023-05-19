hubdle {
    config {
        analysis()
        coverage()
        documentation { //
            changelog()
        }
        publishing()
        versioning {
            semver {
                tagPrefix.set("c")
            }
        }
    }

    kotlin {
        jvm {
            features {
                gradle {
                    versionCatalog {
                        catalogs(rootDir.resolve("gradle/hubdle.libs.versions.toml"))
                    }
                }
            }
        }
    }
}
