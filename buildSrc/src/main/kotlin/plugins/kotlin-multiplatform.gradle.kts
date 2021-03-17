import internal.groupId

plugins {
    kotlin("multiplatform")
    id("code-formatter")
}

group = groupId

kotlin {
    sourceSets {
        all { defaultLanguageSettings }
    }
}
