import com.javiersc.gradle.extensions.version.catalogs.artifact

plugins { //
    `kotlin-dsl`
}

repositories { //
    mavenCentral()
}

dependencies {
    compileOnly(hubdle.plugins.javiersc.semver.artifact)

    compileOnly(hubdle.javiersc.gradle.extensions)
}
