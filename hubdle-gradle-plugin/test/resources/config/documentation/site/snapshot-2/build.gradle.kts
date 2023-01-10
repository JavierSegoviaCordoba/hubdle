plugins {
    id("com.javiersc.hubdle")
}

allprojects {
    version = "1.0.1-SNAPSHOT"
}

hubdle {
    config {
        documentation {
            site {
                reports {
                    allTests.set(false)
                    codeAnalysis.set(false)
                    codeCoverage.set(false)
                    codeQuality.set(false)
                }
            }
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
