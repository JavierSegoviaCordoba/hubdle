@file:Suppress("PropertyName", "VariableNaming")

// Catalog name: pluginLibs

// [versions]
val android = "7.0.2"
val benManes = "0.39.0"
val changelog = "1.1.2"
val detekt = "1.18.1"
val dokka = "1.5.0"
val gradlePublish = "0.13.0"
val javierscGradlePlugins = "0.1.0-alpha.49"
val kotlin = "1.5.30"
val kotlinBinaryValidator = "0.7.1"
val mkdocs = "2.1.1"
val nexusPublish = "1.1.0"
val reckon = "0.13.0"
val spotless = "5.14.3"

// [libraries]
val ajoberstar_reckon_reckonGradle = "org.ajoberstar.reckon:reckon-gradle:$reckon"
val android_toolsBuild_gradle = "com.android.tools.build:gradle:$android"
val diffplug_spotless_spotlessPluginGradle =
    "com.diffplug.spotless:spotless-plugin-gradle:$spotless"
val github_benManes_gradleVersionsPluginX = "com.github.ben-manes:gradle-versions-plugin:$benManes"
val github_gradleNexus_publishPluginX = "io.github.gradle-nexus:publish-plugin:$nexusPublish"
val gitlab_arturboschDetekt_detektGradlePluginX =
    "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detekt"
val gradle_publish_pluginPublishPluginX = "com.gradle.publish:plugin-publish-plugin:$gradlePublish"
val javiersc_gradlePlugins_androidLibrary =
    "com.javiersc.gradle-plugins:android-library:$javierscGradlePlugins"
val javiersc_gradlePlugins_allProjects =
    "com.javiersc.gradle-plugins:all-projects:$javierscGradlePlugins"
val javiersc_gradlePlugins_buildVersionCatalogsUpdater =
    "com.javiersc.gradle-plugins:build-version-catalogs-updater:$javierscGradlePlugins"
val javiersc_gradlePlugins_changelog =
    "com.javiersc.gradle-plugins:changelog:$javierscGradlePlugins"
val javiersc_gradlePlugins_codeAnalysis =
    "com.javiersc.gradle-plugins:code-analysis:$javierscGradlePlugins"
val javiersc_gradlePlugins_codeFormatter =
    "com.javiersc.gradle-plugins:code-formatter:$javierscGradlePlugins"
val javiersc_gradlePlugins_dependencyUpdates =
    "com.javiersc.gradle-plugins:dependency-updates:$javierscGradlePlugins"
val javiersc_gradlePlugins_docs = "com.javiersc.gradle-plugins:docs:$javierscGradlePlugins"
val javiersc_gradlePlugins_gradleWrapperUpdater =
    "com.javiersc.gradle-plugins:gradle-wrapper-updater:$javierscGradlePlugins"
val javiersc_gradlePlugins_kotlinMultiplatform =
    "com.javiersc.gradle-plugins:kotlin-multiplatform:$javierscGradlePlugins"
val javiersc_gradlePlugins_nexus = "com.javiersc.gradle-plugins:nexus:$javierscGradlePlugins"
val javiersc_gradlePlugins_pluginAccessors =
    "com.javiersc.gradle-plugins:plugin-accessors:$javierscGradlePlugins"
val javiersc_gradlePlugins_publishGradlePluginX =
    "com.javiersc.gradle-plugins:publish-gradle-plugin:$javierscGradlePlugins"
val javiersc_gradlePlugins_publishKotlinJvm =
    "com.javiersc.gradle-plugins:publish-kotlin-jvm:$javierscGradlePlugins"
val javiersc_gradlePlugins_readmeBadges =
    "com.javiersc.gradle-plugins:readme-badges:$javierscGradlePlugins"
val javiersc_gradlePlugins_versioning =
    "com.javiersc.gradle-plugins:versioning:$javierscGradlePlugins"
val jetbrains_dokka_dokkaGradlePluginX = "org.jetbrains.dokka:dokka-gradle-plugin:$dokka"
val jetbrains_intellijPlugins_gradleChangelogPluginX =
    "org.jetbrains.intellij.plugins:gradle-changelog-plugin:$changelog"
val jetbrains_kotlin_kotlinGradlePluginX = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin"
val jetbrains_kotlinx_binaryCompatibilityValidator =
    "org.jetbrains.kotlinx:binary-compatibility-validator:$kotlinBinaryValidator"
val vyarus_gradleMkdocsPluginX = "ru.vyarus:gradle-mkdocs-plugin:$mkdocs"

// [bundles]
val javierscPlugins =
    javiersc_gradlePlugins_androidLibrary +
        javiersc_gradlePlugins_allProjects +
        javiersc_gradlePlugins_buildVersionCatalogsUpdater +
        javiersc_gradlePlugins_changelog +
        javiersc_gradlePlugins_codeAnalysis +
        javiersc_gradlePlugins_codeFormatter +
        javiersc_gradlePlugins_dependencyUpdates +
        javiersc_gradlePlugins_docs +
        javiersc_gradlePlugins_gradleWrapperUpdater +
        javiersc_gradlePlugins_kotlinMultiplatform +
        javiersc_gradlePlugins_nexus +
        javiersc_gradlePlugins_pluginAccessors +
        javiersc_gradlePlugins_publishGradlePluginX +
        javiersc_gradlePlugins_publishKotlinJvm +
        javiersc_gradlePlugins_readmeBadges +
        javiersc_gradlePlugins_versioning +
        jetbrains_kotlin_kotlinGradlePluginX +
        jetbrains_kotlinx_binaryCompatibilityValidator
