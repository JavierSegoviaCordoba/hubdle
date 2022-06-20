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
                checkApi = true
                checkFormat = true
                dumpApi = true
            }
        }

        versioning {
            isEnabled = false
        }
    }
}
