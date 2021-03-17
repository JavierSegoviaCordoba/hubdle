import internal.groupId

plugins {
    kotlin("jvm")
    publish
}

group = groupId

dependencies {
    implementation(gradleApi())
}
