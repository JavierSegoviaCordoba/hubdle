import org.jetbrains.changelog.ChangelogPluginExtension

plugins {
    id("com.javiersc.gradle.plugins.changelog")
}

allprojects {
    version = "0.1.0"
}

configure<ChangelogPluginExtension> {
    version.set("0.1.0")
}
