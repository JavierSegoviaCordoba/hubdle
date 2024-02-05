pluginManagement {
    val hubdleVersion: String =
        file("$rootDir/gradle/hubdle.libs.versions.toml")
            .readLines()
            .first { it.startsWith("javiersc-hubdle") }
            .split("\"")[1]

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }


    plugins { //
        id("com.javiersc.hubdle") version hubdleVersion
    }
}

plugins { //
    id("com.javiersc.hubdle.settings")
}

hubdle {
    autoInclude {
        //        excludes(":library")
        //        excludedBuilds("included-library")
    }
}
