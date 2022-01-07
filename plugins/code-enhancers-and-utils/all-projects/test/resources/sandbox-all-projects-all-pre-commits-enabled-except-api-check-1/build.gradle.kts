plugins {
    id("com.javiersc.gradle.plugins.all.projects")
}

allProjectsConfig {
    install {
        preCommit {
            apiCheck.set(false)
        }
    }
}
