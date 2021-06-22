
plugins {
    id("com.gradle.plugin-publish")
}

pluginBundle {
    website = property("pom.smc.url").toString()
    vcsUrl = property("pom.smc.connection").toString()
}
