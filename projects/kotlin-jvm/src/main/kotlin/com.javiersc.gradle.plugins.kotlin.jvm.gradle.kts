
plugins {
    kotlin("jvm")
}

java.sourceSets.all {
    java.srcDirs("$name/java", "$name/kotlin")
    resources.srcDirs("$name/resources")
}

kotlin.sourceSets.all {
    kotlin.srcDirs("$name/java", "$name/kotlin")
    resources.srcDirs("$name/resources")
}
