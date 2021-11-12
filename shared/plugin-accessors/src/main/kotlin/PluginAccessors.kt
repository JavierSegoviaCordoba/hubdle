@file:Suppress(
    "FunctionName",
    "TopLevelPropertyNaming",
    "ObjectPropertyName",
    "RemoveRedundantBackticks",
    "TooManyFunctions"
)

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`android-library`: PluginDependencySpec
    get() = id("com.android.library")

infix fun PluginDependenciesSpec.`android-library`(version: String): PluginDependencySpec =
    id("com.android.library").version(version)

val PluginDependenciesSpec.`android-application`: PluginDependencySpec
    get() = id("com.android.application")

infix fun PluginDependenciesSpec.`android-application`(version: String): PluginDependencySpec =
    id("com.android.application").version(version)

val PluginDependenciesSpec.`auto-include`: PluginDependencySpec
    get() = id("com.pablisco.gradle.auto.include")

infix fun PluginDependenciesSpec.`auto-include`(version: String): PluginDependencySpec =
    id("com.pablisco.gradle.auto.include").version(version)

val PluginDependenciesSpec.`ben-manes-versions`: PluginDependencySpec
    get() = id("com.github.ben-manes.versions")

infix fun PluginDependenciesSpec.`ben-manes-versions`(version: String): PluginDependencySpec =
    id("com.github.ben-manes.versions").version(version)

val PluginDependenciesSpec.`changelog`: PluginDependencySpec
    get() = id("org.jetbrains.changelog")

infix fun PluginDependenciesSpec.`changelog`(version: String): PluginDependencySpec =
    id("org.jetbrains.changelog").version(version)

val PluginDependenciesSpec.`compose`: PluginDependencySpec
    get() = id("org.jetbrains.compose")

infix fun PluginDependenciesSpec.`compose`(version: String): PluginDependencySpec =
    id("org.jetbrains.compose").version(version)

val PluginDependenciesSpec.`compose-resources`: PluginDependencySpec
    get() = id("com.javiersc.compose.resources")

infix fun PluginDependenciesSpec.`compose-resources`(version: String): PluginDependencySpec =
    id("com.javiersc.compose.resources").version(version)

val PluginDependenciesSpec.`hilt-android`: PluginDependencySpec
    get() = id("dagger.hilt.android.plugin")

infix fun PluginDependenciesSpec.`hilt-android`(version: String): PluginDependencySpec =
    id("dagger.hilt.android.plugin").version(version)

val PluginDependenciesSpec.`detekt`: PluginDependencySpec
    get() = id("io.gitlab.arturbosch.detekt")

infix fun PluginDependenciesSpec.`detekt`(version: String): PluginDependencySpec =
    id("io.gitlab.arturbosch.detekt").version(version)

val PluginDependenciesSpec.`dokka`: PluginDependencySpec
    get() = id("org.jetbrains.dokka")

infix fun PluginDependenciesSpec.`dokka`(version: String): PluginDependencySpec =
    id("org.jetbrains.dokka").version(version)

val PluginDependenciesSpec.`gradle-plugin-publish`: PluginDependencySpec
    get() = id("com.gradle.plugin-publish")

infix fun PluginDependenciesSpec.`gradle-plugin-publish`(version: String): PluginDependencySpec =
    id("com.gradle.plugin-publish").version(version)

val PluginDependenciesSpec.`kotlin-android`: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.android")

infix fun PluginDependenciesSpec.`kotlin-android`(version: String): PluginDependencySpec =
    id("org.jetbrains.kotlin.android").version(version)

val PluginDependenciesSpec.`kotlin-jvm`: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.jvm")

infix fun PluginDependenciesSpec.`kotlin-jvm`(version: String): PluginDependencySpec =
    id("org.jetbrains.kotlin.jvm").version(version)

val PluginDependenciesSpec.`kotlin-js`: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.js")

infix fun PluginDependenciesSpec.`kotlin-js`(version: String): PluginDependencySpec =
    id("org.jetbrains.kotlin.js").version(version)

val PluginDependenciesSpec.`kotlin-kapt`: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.kapt")

infix fun PluginDependenciesSpec.`kotlin-kapt`(version: String): PluginDependencySpec =
    id("org.jetbrains.kotlin.kapt").version(version)

val PluginDependenciesSpec.`kotlin-ksp`: PluginDependencySpec
    get() = id("com.google.devtools.ksp")

infix fun PluginDependenciesSpec.`kotlin-ksp`(version: String): PluginDependencySpec =
    id("com.google.devtools.ksp").version(version)

val PluginDependenciesSpec.`kotlin-multiplatform`: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.multiplatform")

infix fun PluginDependenciesSpec.`kotlin-multiplatform`(version: String): PluginDependencySpec =
    id("org.jetbrains.kotlin.multiplatform").version(version)

val PluginDependenciesSpec.`kotlin-parcelize`: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.plugin.parcelize")

infix fun PluginDependenciesSpec.`kotlin-parcelize`(version: String): PluginDependencySpec =
    id("org.jetbrains.kotlin.plugin.parcelize").version(version)

val PluginDependenciesSpec.`kotlin-serialization`: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.plugin.serialization")

infix fun PluginDependenciesSpec.`kotlin-serialization`(version: String): PluginDependencySpec =
    id("org.jetbrains.kotlin.plugin.serialization").version(version)

val PluginDependenciesSpec.`kotlinx-binary-compatibility-validator`: PluginDependencySpec
    get() = id("org.jetbrains.kotlinx.binary-compatibility-validator")

infix fun PluginDependenciesSpec.`kotlin-binary-compatibility-validator`(
    version: String
): PluginDependencySpec =
    id("org.jetbrains.kotlinx.binary-compatibility-validator").version(version)

val PluginDependenciesSpec.`mkdocs`: PluginDependencySpec
    get() = id("ru.vyarus.mkdocs")

infix fun PluginDependenciesSpec.`mkdocs`(version: String): PluginDependencySpec =
    id("ru.vyarus.mkdocs").version(version)

val PluginDependenciesSpec.`nexus-publish`: PluginDependencySpec
    get() = id("io.github.gradle-nexus.publish-plugin")

infix fun PluginDependenciesSpec.`nexus-publish`(version: String): PluginDependencySpec =
    id("io.github.gradle-nexus.publish-plugin").version(version)

val PluginDependenciesSpec.`reckon`: PluginDependencySpec
    get() = id("org.ajoberstar.reckon")

infix fun PluginDependenciesSpec.`reckon`(version: String): PluginDependencySpec =
    id("org.ajoberstar.reckon").version(version)

val PluginDependenciesSpec.`semver`: PluginDependencySpec
    get() = id("com.javiersc.semver.gradle.plugin")

infix fun PluginDependenciesSpec.`semver`(version: String): PluginDependencySpec =
    id("com.javiersc.semver.gradle.plugin").version(version)

val PluginDependenciesSpec.`spotless`: PluginDependencySpec
    get() = id("com.diffplug.spotless")

infix fun PluginDependenciesSpec.`spotless`(version: String): PluginDependencySpec =
    id("com.diffplug.spotless").version(version)
