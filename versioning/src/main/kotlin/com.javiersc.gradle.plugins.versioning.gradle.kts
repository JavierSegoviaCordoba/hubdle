
plugins {
    id("org.ajoberstar.reckon")
}

reckon {
    scopeFromProp()
    if (properties["isSnapshot"]?.toString()?.toBoolean() == true) snapshotFromProp()
    else stageFromProp("alpha", "beta", "rc", "final")
}

File("${rootProject.buildDir}/versioning/version.txt").apply {
    if (!exists()) {
        parentFile.mkdirs()
        createNewFile()
    }
    writeText(version.toString())
}
