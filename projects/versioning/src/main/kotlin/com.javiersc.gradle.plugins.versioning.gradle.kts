import org.ajoberstar.reckon.gradle.ReckonExtension

plugins {
    id("org.ajoberstar.reckon")
}

configure<ReckonExtension> {
    scopeFromProp()
    if (properties["reckon.stage"]?.toString().equals("snapshot", true)) snapshotFromProp()
    else stageFromProp("alpha", "beta", "rc", "final")
}

File("${rootProject.buildDir}/versioning/version.txt").apply {
    if (!exists()) {
        parentFile.mkdirs()
        createNewFile()
    }
    writeText(version.toString())
}
