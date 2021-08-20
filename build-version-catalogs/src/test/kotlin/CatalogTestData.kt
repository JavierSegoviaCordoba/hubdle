@file:Suppress("MaxLineLength", "PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

enum class CatalogTestData(val rawData: String, val expect: String) {
    Data_0_0(
        rawData =
            """
               |// [metadata]
               |val format_version = "1.1"
               |
               |// [versions]
               |val kotlin = "1.5.10"
               |val kotlin2 = "1.5.21
               |
               |// [libraries]
               |val jetbrains_kotlin_kotlinTest = "org.jetbrains.kotlin:kotlin-test"
               |val jetbrains_kotlinx_kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${'$'}coroutines"
               |val kotest_kotestAssertionsCore = "io.kotest:kotest-assertions-core:4.6.1"
               |
               |// [bundles]
               |val bundle_1 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore
               |val bundle2 = jetbrains_kotlin_kotlinTest + kotest_kotestAssertionsCore
               |
               |// [plugins]
               |val kotlin_multiplatform = "org.jetbrains.kotlin.multiplatform" to kotlin
               |val kotlin_serialization = "org.jetbrains.kotlin.plugin.serialization" to "1.5.21"
               |
            """.trimMargin(),
        expect =
            """
               |[metadata]
               |format.version = "1.1"
               |
               |[versions]
               |kotlin = "1.5.10"
               |kotlin2 = "1.5.21"
               |
               |[libraries]
               |jetbrains-kotlin-kotlinTest = { module = "org.jetbrains.kotlin:kotlin-test" }
               |jetbrains-kotlinx-kotlinxCoroutinesCore = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
               |kotest-kotestAssertionsCore = { module = "io.kotest:kotest-assertions-core", version = "4.6.1" }
               |
               |[bundles]
               |bundle-1 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore"]
               |bundle2 = ["jetbrains-kotlin-kotlinTest", "kotest-kotestAssertionsCore"]
               |
               |[plugins]
               |kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
               |kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version = "1.5.21" }
               |
            """.trimMargin(),
    ),
    Data_0_1(
        rawData =
            """
               |// Catalog name: libs
               |// [metadata]
               |val format_version = "1.1"
               |
               |// [versions]
               |val kotlin = "1.5.10"
               |val kotlin2 = "1.5.21
               |
               |// [libraries]
               |val jetbrains_kotlin_kotlinTest = "org.jetbrains.kotlin:kotlin-test"
               |val jetbrains_kotlinx_kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${'$'}coroutines"
               |val kotest_kotestAssertionsCore = "io.kotest:kotest-assertions-core:4.6.1"
               |
               |// [bundles]
               |val bundle_1 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore
               |val bundle2 = jetbrains_kotlin_kotlinTest + kotest_kotestAssertionsCore
               |
               |// [plugins]
               |val kotlin_multiplatform = "org.jetbrains.kotlin.multiplatform" to kotlin
               |val kotlin_serialization = "org.jetbrains.kotlin.plugin.serialization" to "1.5.21"
               |
            """.trimMargin(),
        expect =
            """
               |[metadata]
               |format.version = "1.1"
               |
               |[versions]
               |kotlin = "1.5.10"
               |kotlin2 = "1.5.21"
               |
               |[libraries]
               |jetbrains-kotlin-kotlinTest = { module = "org.jetbrains.kotlin:kotlin-test" }
               |jetbrains-kotlinx-kotlinxCoroutinesCore = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
               |kotest-kotestAssertionsCore = { module = "io.kotest:kotest-assertions-core", version = "4.6.1" }
               |
               |[bundles]
               |bundle-1 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore"]
               |bundle2 = ["jetbrains-kotlin-kotlinTest", "kotest-kotestAssertionsCore"]
               |
               |[plugins]
               |kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
               |kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version = "1.5.21" }
               |
            """.trimMargin(),
    ),
    Data_1_0(
        rawData =
            """
               |// [metadata]
               |val format_version = "1.1"
               |
            """.trimMargin(),
        expect =
            """
               |[metadata]
               |format.version = "1.1"
               |
            """.trimMargin(),
    ),
    Data_1_1(
        rawData =
            """
               |// [metadata]
               |val format_version =
               |    "1.1"
               |    
            """.trimMargin(),
        expect =
            """
               |[metadata]
               |format.version = "1.1"
               |
            """.trimMargin()
    ),
    Data_2_0(
        rawData =
            """
               |// [versions]
               |val kotlin = "1.5.10"
               |val kotlin2 = "1.5.21
               |
            """.trimMargin(),
        expect =
            """
               |[versions]
               |kotlin = "1.5.10"
               |kotlin2 = "1.5.21"
               |
            """.trimMargin(),
    ),
    Data_2_1(
        rawData =
            """
               |// [versions]
               |val kotlin = "1.5.10"
               |val kotlin2 =
               |    "1.5.21
               |    
            """.trimMargin(),
        expect =
            """
               |[versions]
               |kotlin = "1.5.10"
               |kotlin2 = "1.5.21"
               |
            """.trimMargin(),
    ),
    Data_3_0(
        rawData =
            """
               |// [libraries]
               |val jetbrains_kotlin_kotlinTest = "org.jetbrains.kotlin:kotlin-test"
               |val jetbrains_kotlinx_kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${'$'}coroutines"
               |val kotest_kotestAssertionsCore = "io.kotest:kotest-assertions-core:4.6.1"
               |
            """.trimMargin(),
        expect =
            """
               |[libraries]
               |jetbrains-kotlin-kotlinTest = { module = "org.jetbrains.kotlin:kotlin-test" }
               |jetbrains-kotlinx-kotlinxCoroutinesCore = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
               |kotest-kotestAssertionsCore = { module = "io.kotest:kotest-assertions-core", version = "4.6.1" }
               |
            """.trimMargin(),
    ),
    Data_3_1(
        rawData =
            """
               |// [libraries]
               |val jetbrains_kotlin_kotlinTest = "org.jetbrains.kotlin:kotlin-test"
               |val jetbrains_kotlinx_kotlinxCoroutinesCore =
               |     "org.jetbrains.kotlinx:kotlinx-coroutines-core:${'$'}coroutines"
               |val kotest_kotestAssertionsCore = "io.kotest:kotest-assertions-core:4.6.1"
               |
            """.trimMargin(),
        expect =
            """
               |[libraries]
               |jetbrains-kotlin-kotlinTest = { module = "org.jetbrains.kotlin:kotlin-test" }
               |jetbrains-kotlinx-kotlinxCoroutinesCore = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core", version.ref = "coroutines" }
               |kotest-kotestAssertionsCore = { module = "io.kotest:kotest-assertions-core", version = "4.6.1" }
               |
            """.trimMargin(),
    ),
    Data_4_0(
        rawData =
            """
               |// [bundles]
               |val bundle_1 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore
               |val bundle2 = jetbrains_kotlin_kotlinTest + kotest_kotestAssertionsCore
               |
            """.trimMargin(),
        expect =
            """
               |[bundles]
               |bundle-1 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore"]
               |bundle2 = ["jetbrains-kotlin-kotlinTest", "kotest-kotestAssertionsCore"]
               |
            """.trimMargin(),
    ),
    Data_4_1(
        rawData =
            """
               |// [bundles]
               |val bundle_1 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore
               |val bundle2 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore +
               |    kotest_kotestAssertionsCore
               |
            """.trimMargin(),
        expect =
            """
               |[bundles]
               |bundle-1 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore"]
               |bundle2 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore", "kotest-kotestAssertionsCore"]
               |
            """.trimMargin(),
    ),
    Data_4_2(
        rawData =
            """
               |// [bundles]
               |val bundle_1 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore
               |val bundle2 = jetbrains_kotlin_kotlinTest + 
               |    jetbrains_kotlinx_kotlinxCoroutinesCore +
               |    kotest_kotestAssertionsCore
               |val bundle3 = jetbrains_kotlin_kotlinTest + kotest_kotestAssertionsCore
               |
            """.trimMargin(),
        expect =
            """
               |[bundles]
               |bundle-1 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore"]
               |bundle2 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore", "kotest-kotestAssertionsCore"]
               |bundle3 = ["jetbrains-kotlin-kotlinTest", "kotest-kotestAssertionsCore"]
               |
            """.trimMargin(),
    ),
    Data_4_3(
        rawData =
            """
               |// [bundles]
               |val bundle_1 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore
               |val bundle2 = jetbrains_kotlin_kotlinTest + 
               |    jetbrains_kotlinx_kotlinxCoroutinesCore +
               |    kotest_kotestAssertionsCore
               |
            """.trimMargin(),
        expect =
            """
               |[bundles]
               |bundle-1 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore"]
               |bundle2 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore", "kotest-kotestAssertionsCore"]
               |
            """.trimMargin(),
    ),
    Data_4_4(
        rawData =
            """
               |// [bundles]
               |val bundle_1 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore
               |val bundle2 = 
               |    jetbrains_kotlin_kotlinTest + 
               |    jetbrains_kotlinx_kotlinxCoroutinesCore +
               |    kotest_kotestAssertionsCore
               |val bundle3 = jetbrains_kotlin_kotlinTest + kotest_kotestAssertionsCore
               |
            """.trimMargin(),
        expect =
            """
               |[bundles]
               |bundle-1 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore"]
               |bundle2 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore", "kotest-kotestAssertionsCore"]
               |bundle3 = ["jetbrains-kotlin-kotlinTest", "kotest-kotestAssertionsCore"]
               |
            """.trimMargin(),
    ),
    Data_5_0(
        rawData =
            """
               |// [plugins]
               |val kotlin_multiplatform = "org.jetbrains.kotlin.multiplatform" to kotlin
               |val kotlin_serialization = "org.jetbrains.kotlin.plugin.serialization" to "1.5.21"
               |
            """.trimMargin(),
        expect =
            """
               |[plugins]
               |kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
               |kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version = "1.5.21" }
               |
            """.trimMargin(),
    ),
    Data_5_1(
        rawData =
            """
               |// [plugins]
               |val kotlin_multiplatform = 
               |    "org.jetbrains.kotlin.multiplatform" to kotlin
               |val kotlin_serialization = "org.jetbrains.kotlin.plugin.serialization" to "1.5.21"
               |
            """.trimMargin(),
        expect =
            """
               |[plugins]
               |kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
               |kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version = "1.5.21" }
               |
            """.trimMargin(),
    )
}
