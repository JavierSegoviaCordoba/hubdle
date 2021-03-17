import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.accessors.dm.LibrariesForPluginLibs

plugins {
    id("com.diffplug.spotless")
}

configure<SpotlessExtension> {
    kotlin {
        target("src/**/*.kt")
        ktfmt(ktfmtVersion).kotlinlangStyle()
    }
}

internal val Project.ktfmtVersion: String
    get() = the<LibrariesForPluginLibs>().versions.ktfmt.get()
