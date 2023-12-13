@file:Suppress("PackageDirectoryMismatch")

plugins {
    id("com.javiersc.hubdle")
}

hubdle {
    config {
        install {
            preCommits {
                allTests.set(true)
                applyFormat.set(true)
                assemble.set(true)
                checkAnalysis.set(true)
                checkApi.set(false)
            }
        }

        versioning {
            isEnabled.set(false)
        }
    }
}
