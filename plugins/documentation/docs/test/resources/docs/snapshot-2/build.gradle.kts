plugins {
    id("com.javiersc.gradle.plugins.docs")
}

allprojects {
    version = "0.1.1-SNAPSHOT"
}

docs {
    navigation {
        reports {
            allTests.set(false)
            codeAnalysis.set(false)
            codeCoverage.set(false)
            codeQuality.set(false)
        }
    }
}
