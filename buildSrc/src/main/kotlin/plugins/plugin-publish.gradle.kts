
plugins {
    id("com.gradle.plugin-publish")
}

pluginBundle {
    website = property("pomSmcUrl").toString()
    vcsUrl = property("pomSmcConnection").toString()
}
