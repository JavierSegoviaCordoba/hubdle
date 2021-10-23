import com.gradle.publish.PluginBundleExtension
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
    from((project.properties["sourceSets"] as SourceSetContainer)["main"].allSource)
}

configurePublishing(artifacts = listOf(docsJar, sourcesJar))

configure(SigningExtension::signPublications)

extensions.findByType<PluginBundleExtension>()?.apply {
    website = property("pom.url").toString()
    vcsUrl = property("pom.smc.url").toString()
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
