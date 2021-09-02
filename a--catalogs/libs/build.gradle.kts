@file:Suppress("PropertyName", "VariableNaming")

// Catalog name: libs

// [versions]
val coroutines = "1.5.2-native-mt"
val gson = "2.8.8"
val jsoup = "1.14.2"
val kotest = "4.6.2"
val ktfmt = "0.28"
val semanticVersioning = "0.1.0-alpha.4"

// [libraries]
val facebook_ktfmt = "com.facebook:ktfmt:$ktfmt"
val google_codeGson_gson = "com.google.code.gson:gson:$gson"
val javiersc_semanticVersioning_semanticVersioningCore =
    "com.javiersc.semantic-versioning:semantic-versioning-core:$semanticVersioning"
val jetbrains_kotlin_kotlinTest = "org.jetbrains.kotlin:kotlin-test"
val jetbrains_kotlin_kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit"
val jetbrains_kotlinx_kotlinxCoroutinesCore =
    "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
val jsoup_jsoup = "org.jsoup:jsoup:$jsoup"
val kotest_kotestAssertionsCore = "io.kotest:kotest-assertions-core:$kotest"

// [bundles]
val testing = jetbrains_kotlin_kotlinTest + kotest_kotestAssertionsCore
