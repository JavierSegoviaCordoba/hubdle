import com.javiersc.plugins.core.groupId

plugins { kotlin("multiplatform") }

group = groupId

kotlin {
    sourceSets {
        all { defaultLanguageSettings }
    }
}
