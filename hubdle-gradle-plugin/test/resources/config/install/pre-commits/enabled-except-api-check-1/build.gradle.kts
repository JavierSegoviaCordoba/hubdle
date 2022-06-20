plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        install {
            preCommits {
                allTests = true
                applyFormat = true
                assemble = true
                checkAnalysis = true
                checkApi = false
            }
        }

        versioning {
            isEnabled = false
        }
    }
}
