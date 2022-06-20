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
                    allTests = false
                    codeAnalysis = false
                    codeCoverage = false
                    codeQuality = false
                }
            }
        }

        versioning {
            isEnabled = false
        }
    }
}
