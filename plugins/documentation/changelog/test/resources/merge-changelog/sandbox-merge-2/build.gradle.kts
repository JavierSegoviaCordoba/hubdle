plugins {
    id("com.javiersc.gradle.plugins.changelog")
}

allprojects {
    version = "0.1.0"
}

changelog {
    version.set("0.1.0")
}
