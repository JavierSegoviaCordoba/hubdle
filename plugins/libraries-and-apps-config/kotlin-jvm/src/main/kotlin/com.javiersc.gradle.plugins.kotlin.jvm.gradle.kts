import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    kotlin("jvm")
}

configure<JavaPluginExtension> {
    sourceSets.all {
        java.srcDirs("$name/java", "$name/kotlin")
        resources.srcDirs("$name/resources")
    }
}

configure<KotlinJvmProjectExtension> {
    sourceSets.all {
        kotlin.srcDirs("$name/java", "$name/kotlin")
        resources.srcDirs("$name/resources")
    }
}
