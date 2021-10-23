import com.android.build.gradle.LibraryExtension
import com.javiersc.plugins.core.isSignificant
import com.javiersc.plugins.publishing.core.configurePublishing
import com.javiersc.plugins.publishing.core.signPublications

plugins {
    `maven-publish`
    signing
}

val docsJar by project.tasks.creating(Jar::class) {
    group = "build"
    description = "Assembles Javadoc jar file from for publishing"
    archiveClassifier.set("javadoc")
}

val sourcesJar by project.tasks.creating(Jar::class) {
    group = "build"
    description = "Assembles Sources jar file for publishing"
    archiveClassifier.set("sources")
    from(
        (project.extensions.getByName("android") as LibraryExtension)
            .sourceSets
            .named("main")
            .get()
            .java
            .srcDirs,
    )
}

afterEvaluate {
    configurePublishing(
        artifacts = listOf(docsJar, sourcesJar),
        components = mapOf("release" to "release"),
    )

    configure(SigningExtension::signPublications)
}

project.tasks { create<Exec>("gitDiff") { commandLine("git", "diff") } }

val checkIsSignificant: Task by project.tasks.creating {
    dependsOn("gitDiff")
    doLast {
        if (!isSignificant) {
            error("Only significant versions can be published (current: $version)")
        }
    }
}

rootProject.tasks {
    getByName("initializeSonatypeStagingRepository") { dependsOn(checkIsSignificant) }
}

tasks {
    getByName("publish") { dependsOn(checkIsSignificant) }

    getByName("publishToSonatype") { dependsOn(checkIsSignificant) }

    getByName("publishToMavenLocal") { dependsOn(checkIsSignificant) }
}
