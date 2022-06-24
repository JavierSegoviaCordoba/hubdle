plugins {
    id("com.javiersc.hubdle")
}

allprojects {
    version = "1.0.0-alpha.1"
}

hubdle {
    config {
        documentation {
            readmeBadges {
                kotlin = false
                mavenCentral = false
                snapshots = false
                build = true
                coverage = false
                quality = false
                techDebt = false
            }
        }
    }
}
