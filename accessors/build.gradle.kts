plugins {
    kotlin("jvm")
    publish
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation(gradleApi())
}
