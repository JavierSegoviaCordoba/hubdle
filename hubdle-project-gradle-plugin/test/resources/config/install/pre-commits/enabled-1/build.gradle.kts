plugins {
    id("com.javiersc.hubdle.project")
}

hubdle {
    config {
        install {
            preCommits {
                allTests.set(true)
                applyFormat.set(true)
                assemble.set(true)
                checkAnalysis.set(true)
                checkApi.set(true)
                checkFormat.set(true)
                dumpApi.set(true)
            }
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
