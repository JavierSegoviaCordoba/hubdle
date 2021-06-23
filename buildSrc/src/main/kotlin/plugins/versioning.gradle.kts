import internal.isSignificant

plugins {
    id("org.ajoberstar.reckon")
}

reckon {
    scopeFromProp()
    if (properties["isSnapshot"]?.toString()?.toBoolean() == true) snapshotFromProp()
    else stageFromProp("alpha", "beta", "rc", "final")
}

rootProject.allprojects {
    tasks {
        register("checkIsSignificant") {
            group = "verification"

            if (!isSignificant) {
                error("Only significant versions can be published (current: $version)")
            }
        }
    }
}

File("${rootProject.buildDir}/versioning/version.txt").apply {
    if (!exists()) {
        parentFile.mkdirs()
        createNewFile()
    }
    writeText(version.toString())
}
