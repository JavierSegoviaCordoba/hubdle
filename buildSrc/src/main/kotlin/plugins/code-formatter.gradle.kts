
plugins {
    id("com.diffplug.spotless")
}

spotless {
    kotlin {
        target("src/**/*.kt")
        ktfmt(ktfmtVersion).kotlinlangStyle()
    }
}

internal val Project.ktfmtVersion: String
    get() = pluginLibsAccessors.versions.ktfmt.get()
