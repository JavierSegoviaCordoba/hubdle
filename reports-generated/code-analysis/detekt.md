# detekt

## Metrics

* 955 number of properties

* 595 number of functions

* 255 number of classes

* 79 number of packages

* 207 number of kt files

## Complexity Report

* 11,771 lines of code (loc)

* 9,757 source lines of code (sloc)

* 6,620 logical lines of code (lloc)

* 52 comment lines of code (cloc)

* 1,247 cyclomatic complexity (mcc)

* 535 cognitive complexity

* 97 number of total code smells

* 0% comment source ratio

* 188 mcc per 1,000 lloc

* 14 code smells per 1,000 lloc

## Findings (97)

### complexity, ComplexCondition (1)

Complex conditions should be simplified and extracted into well-named methods if necessary.

[Documentation](https://detekt.dev/docs/rules/complexity#complexcondition)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/site/PrebuildSiteTask.kt:383:25
```
This condition is too complex (4). Defined complexity threshold for conditions is set to '4'
```
```kotlin
380 
381         val navReports: String =
382             buildList {
383                     if (allTests && codeAnalysis || codeCoverage || codeQuality) add("  - Reports:")
!!!                         ^ error
384                     if (allTests) createReportSection("All tests", "all-tests")
385                     if (codeAnalysis) createReportSection("Code analysis", "code-analysis")
386                     if (codeCoverage) createReportSection("Code coverage", "code-coverage")

```

### complexity, ComplexMethod (1)

Prefer splitting up complex methods into smaller, easier to understand methods.

[Documentation](https://detekt.dev/docs/rules/complexity#complexmethod)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/_internal/configureBuildFeatures.kt:20:21
```
The function configureFeatures appears to be too complex (15). Defined complexity threshold for methods is set to '15'
```
```kotlin
17     }
18 }
19 
20 private fun Project.configureFeatures(androidCommonExtension: AndroidCommonExtension) {
!!                     ^ error
21     val feats = project.hubdleState.kotlin.android.buildFeatures
22     androidCommonExtension.buildFeatures {
23         aidl = feats.aidl ?: propOrNull(BuildFeatures.aidl) ?: trueIfApp()

```

### complexity, LongMethod (3)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* hubdle-gradle-plugin/build.gradle.kts:168:13
```
The function buildHubdleDependencies is too long (70). The maximum length is 60.
```
```kotlin
165         }
166 }
167 
168 fun Project.buildHubdleDependencies() {
!!!             ^ error
169     buildDir.resolve("generated/main/kotlin/hubdle_dependencies.kt").apply {
170         parentFile.mkdirs()
171         createNewFile()

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformDarwin.kt:11:14
```
The function configureMultiplatformDarwin is too long (80). The maximum length is 60.
```
```kotlin
8  import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
9  import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS
10 
11 internal fun configureMultiplatformDarwin(project: Project) {
!!              ^ error
12     if (project.hubdleState.kotlin.multiplatform.darwin.isEnabled) {
13         project.configure<KotlinMultiplatformExtension> {
14             val commonMain: KSS by sourceSets.getting

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformNative.kt:11:14
```
The function configureMultiplatformNative is too long (112). The maximum length is 60.
```
```kotlin
8  import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
9  import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet as KSS
10 
11 internal fun configureMultiplatformNative(project: Project) {
!!              ^ error
12     if (project.hubdleState.kotlin.multiplatform.native.isEnabled) {
13         project.configure<KotlinMultiplatformExtension> {
14             val commonMain: KSS by sourceSets.getting

```

### complexity, NestedBlockDepth (1)

Excessive nesting leads to hidden complexity. Prefer extracting code to make it easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#nestedblockdepth)

* hubdle-gradle-plugin/build.gradle.kts:168:13
```
Function buildHubdleDependencies is nested too deeply.
```
```kotlin
165         }
166 }
167 
168 fun Project.buildHubdleDependencies() {
!!!             ^ error
169     buildDir.resolve("generated/main/kotlin/hubdle_dependencies.kt").apply {
170         parentFile.mkdirs()
171         createNewFile()

```

### complexity, TooManyFunctions (4)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/HubdleConfigExtension.kt:25:19
```
Class 'HubdleConfigExtension' with '11' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
22 import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
23 
24 @HubdleDslMarker
25 public open class HubdleConfigExtension
!!                   ^ error
26 @Inject
27 constructor(
28     objects: ObjectFactory,

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/language/settings/HubdleLanguageSettingsExtension.kt:12:19
```
Class 'HubdleLanguageSettingsExtension' with '11' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
9  import org.gradle.kotlin.dsl.newInstance
10 import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder
11 
12 public open class HubdleLanguageSettingsExtension @Inject constructor(objects: ObjectFactory) {
!!                   ^ error
13 
14     @HubdleDslMarker
15     public fun Project.experimentalContracts(enabled: Boolean = true) {

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/HubdleKotlinMultiplatformExtension.kt:61:19
```
Class 'HubdleKotlinMultiplatformExtension' with '38' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
58 import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
59 
60 @HubdleDslMarker
61 public open class HubdleKotlinMultiplatformExtension
!!                   ^ error
62 @Inject
63 constructor(
64     objects: ObjectFactory,

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatform.kt:3:1
```
File '/home/runner/work/hubdle/hubdle/hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatform.kt' with '11' functions detected. Defined threshold inside files is set to '11'
```
```kotlin
1 @file:Suppress("SpreadOperator")
2 
3 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
4 
5 import com.android.build.api.dsl.LibraryExtension
6 import com.javiersc.hubdle.extensions._internal.PluginIds

```

### empty-blocks, EmptyKtFile (1)

Empty block of code detected. As they serve no purpose they should be removed.

[Documentation](https://detekt.dev/docs/rules/empty-blocks#emptyktfile)

* sandbox/consumer-settings/included-library/settings.gradle.kts:1:1
```
The empty Kotlin file /home/runner/work/hubdle/hubdle/sandbox/consumer-settings/included-library/settings.gradle.kts can be removed.
```
```kotlin
1 
! ^ error

```

### naming, InvalidPackageDeclaration (28)

Kotlin source files should be stored in the directory corresponding to its package statement.

[Documentation](https://detekt.dev/docs/rules/naming#invalidpackagedeclaration)

* sandbox/consumer-kotlin-jvm/main/kotlin/Main.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.javiersc.gradle.plugins.sandbox
! ^ error
2 
3 fun main() {
4     println(

```

* sandbox/consumer-kotlin-multiplatform/androidMain/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "android"

```

* sandbox/consumer-kotlin-multiplatform/commonMain/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 expect object Platform {
4     val name: String

```

* sandbox/consumer-kotlin-multiplatform/iosArm32Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "iosArm32"

```

* sandbox/consumer-kotlin-multiplatform/iosArm64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "iosArm64"

```

* sandbox/consumer-kotlin-multiplatform/iosSimulatorArm64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "iosSimulatorArm64"

```

* sandbox/consumer-kotlin-multiplatform/iosX64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "iosX64"

```

* sandbox/consumer-kotlin-multiplatform/jsMain/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "js"

```

* sandbox/consumer-kotlin-multiplatform/jvmMain/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "jvm"

```

* sandbox/consumer-kotlin-multiplatform/linuxArm32HfpMain/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "linuxArm32Hfp"

```

* sandbox/consumer-kotlin-multiplatform/linuxArm64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "linuxArm64"

```

* sandbox/consumer-kotlin-multiplatform/linuxMips32Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "linuxMips32"

```

* sandbox/consumer-kotlin-multiplatform/linuxMipsel32Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "linuxMipsel32"

```

* sandbox/consumer-kotlin-multiplatform/linuxX64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "linuxX64"

```

* sandbox/consumer-kotlin-multiplatform/macosArm64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "macosArm64"

```

* sandbox/consumer-kotlin-multiplatform/macosMain/kotlin/MacosMain.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 object MacosMain {
4     fun platform() = Platform.name

```

* sandbox/consumer-kotlin-multiplatform/macosX64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "macosX64"

```

* sandbox/consumer-kotlin-multiplatform/mingwX64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "mingwX64"

```

* sandbox/consumer-kotlin-multiplatform/mingwX86Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "mingwX86"

```

* sandbox/consumer-kotlin-multiplatform/tvosArm64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "tvosArm64"

```

* sandbox/consumer-kotlin-multiplatform/tvosSimulatorArm64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "tvosSimulatorArm64"

```

* sandbox/consumer-kotlin-multiplatform/tvosX64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "tvosX64"

```

* sandbox/consumer-kotlin-multiplatform/wasm32Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "wasm32"

```

* sandbox/consumer-kotlin-multiplatform/watchosArm32Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "watchosArm32"

```

* sandbox/consumer-kotlin-multiplatform/watchosArm64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "watchosArm64"

```

* sandbox/consumer-kotlin-multiplatform/watchosSimulatorArm64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "watchosSimulatorArm64"

```

* sandbox/consumer-kotlin-multiplatform/watchosX64Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "watchosX64"

```

* sandbox/consumer-kotlin-multiplatform/watchosX86Main/kotlin/Platform.kt:1:1
```
The package declaration does not match the actual file location.
```
```kotlin
1 package com.kotlin.multiplatform.sandbox.project
! ^ error
2 
3 actual object Platform {
4     actual val name: String = "watchosX86"

```

### naming, MatchingDeclarationName (1)

If a source file contains only a single non-private top-level class or object, the file name should reflect the case-sensitive name plus the .kt extension.

[Documentation](https://detekt.dev/docs/rules/naming#matchingdeclarationname)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/options/DependenciesOptions.kt:14:18
```
The file name 'DependenciesOptions' does not match the name of the single top-level declaration 'GradleDependenciesOptions'.
```
```kotlin
11  * val Property.dependencies: DependencyHandler
12  * ```
13  */
14 public interface GradleDependenciesOptions {
!!                  ^ error
15     public fun Project.gradleApi(): Dependency = dependencies.gradleApi()
16     public fun Project.localGroovy(): Dependency = dependencies.localGroovy()
17     public fun Project.gradleTestKit(): Dependency = dependencies.gradleTestKit()

```

### naming, PackageNaming (47)

Package names should match the naming convention set in the configuration.

[Documentation](https://detekt.dev/docs/rules/naming#packagenaming)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/PluginIds.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 internal object PluginIds {
4 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/ProjectType.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import org.gradle.api.Project
4 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/state/CatalogDependencies.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal.state
! ^ error
2 
3 import com.javiersc.gradle.extensions.version.catalogs.catalogNamesWithLibsAtFirst
4 import com.javiersc.gradle.extensions.version.catalogs.getLibraries

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/state/HubdleState.kt:3:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 @file:Suppress("MagicNumber")
2 
3 package com.javiersc.hubdle.extensions._internal.state
! ^ error
4 
5 import com.android.build.api.dsl.ApplicationExtension
6 import com.android.build.api.dsl.LibraryExtension

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/state/checkCompatibility.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal.state
! ^ error
2 
3 import org.gradle.api.Project
4 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/_internal/hasKotlinGradlePlugin.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config._internal
! ^ error
2 
3 import org.gradle.api.Project
4 import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/analysis/_internal/configureAnalysis.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.analysis._internal
! ^ error
2 
3 import com.javiersc.gradle.project.extensions.isRootProject
4 import com.javiersc.gradle.properties.extensions.getProperty

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/binary/compatibility/validator/_internal/configureBinaryCompatibilityValidator.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.binary.compatibility.validator._internal
! ^ error
2 
3 import com.javiersc.gradle.project.extensions.isRootProject
4 import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/coverage/_internal/configureCoverage.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.coverage._internal
! ^ error
2 
3 import com.javiersc.gradle.project.extensions.isRootProject
4 import com.javiersc.hubdle.extensions._internal.PluginIds

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/changelog/_internal/Changelog.kt:3:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 @file:Suppress("SpreadOperator")
2 
3 package com.javiersc.hubdle.extensions.config.documentation.changelog._internal
! ^ error
4 
5 import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version
6 import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version.Group

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/changelog/_internal/Extensions.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.documentation.changelog._internal
! ^ error
2 
3 import java.io.File
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/changelog/_internal/MergeChangelog.kt:3:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 @file:Suppress("SpreadOperator")
2 
3 package com.javiersc.hubdle.extensions.config.documentation.changelog._internal
! ^ error
4 
5 internal fun Changelog.merged(): Changelog {
6     val versionsMap: Map<Changelog.Version, List<Changelog.Version>> = buildMap {

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/changelog/_internal/configureChangelog.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.documentation.changelog._internal
! ^ error
2 
3 import com.javiersc.gradle.tasks.extensions.namedLazily
4 import com.javiersc.hubdle.extensions._internal.PluginIds

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/readme/_internal/configureReadme.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.documentation.readme._internal
! ^ error
2 
3 import com.javiersc.gradle.properties.extensions.getProperty
4 import com.javiersc.gradle.properties.extensions.getPropertyOrNull

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/readme/_internal/models/MavenRepo.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.documentation.readme._internal.models
! ^ error
2 
3 /** Sonatype Maven repos */
4 internal enum class MavenRepo {

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/readme/_internal/models/Sonar.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.documentation.readme._internal.models
! ^ error
2 
3 /** Sonar metrics */
4 internal enum class Sonar(val label: String, val path: String) {

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/site/_internal/configureSite.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.documentation.site._internal
! ^ error
2 
3 import com.javiersc.gradle.project.extensions.isRootProject
4 import com.javiersc.hubdle.extensions._internal.PluginIds

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/explicit/api/_internal/configureExplicitApi.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.explicit.api._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/format/_internal/configureFormat.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.format._internal
! ^ error
2 
3 import com.diffplug.gradle.spotless.SpotlessExtension
4 import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/install/_internal/configureInstall.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.install._internal
! ^ error
2 
3 import com.javiersc.gradle.project.extensions.isRootProject
4 import com.javiersc.hubdle.extensions._internal.state.hubdleState

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/language/settings/_internal/configureConfigLanguageSettings.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.language.settings._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.HubdleState.Config.LanguageSettings
4 import com.javiersc.hubdle.extensions._internal.state.hubdleState

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/nexus/_internal/configureNexus.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.nexus._internal
! ^ error
2 
3 import com.javiersc.gradle.properties.extensions.getPropertyOrNull
4 import com.javiersc.hubdle.HubdleProperty.Nexus

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/publishing/_internal/configurePublishing.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.publishing._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/versioning/_internal/configureVersioning.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.versioning._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.PluginIds
4 import com.javiersc.hubdle.extensions._internal.state.hubdleState

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/_internal/configJvmTarget.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.JavaVersion

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/_internal/calculateAndroidNamespace.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.android._internal
! ^ error
2 
3 import com.javiersc.gradle.properties.extensions.getBooleanProperty
4 import com.javiersc.hubdle.HubdleProperty.Android.namespaceUseKotlinFile

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/_internal/configureBuildFeatures.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.android._internal
! ^ error
2 
3 import com.android.build.api.dsl.BuildFeatures as AndroidBuildFeatures
4 import com.android.build.api.dsl.BuildType

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/application/_internal/configureAndroidApplication.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.android.application._internal
! ^ error
2 
3 import com.android.build.api.dsl.ApplicationExtension
4 import com.javiersc.hubdle.extensions._internal.PluginIds

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/library/_internal/configureAndroidLibrary.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.android.library._internal
! ^ error
2 
3 import com.android.build.gradle.LibraryExtension
4 import com.javiersc.hubdle.extensions._internal.PluginIds

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/gradle/plugin/_internal/configureGradlePlugin.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal
! ^ error
2 
3 import com.gradle.publish.PluginBundleExtension
4 import com.javiersc.gradle.properties.extensions.getProperty

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/gradle/version/catalog/_internal/configureGradleVersionCatalog.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.PluginIds
4 import com.javiersc.hubdle.extensions._internal.state.hubdleState

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/intellij/_internal/configureIntelliJ.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.intellij._internal
! ^ error
2 
3 import com.javiersc.gradle.properties.extensions.getProperty
4 import com.javiersc.gradle.properties.extensions.getPropertyOrNull

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/jvm/_internal/configureJvm.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.jvm._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.PluginIds
4 import com.javiersc.hubdle.extensions._internal.state.HubdleState

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatform.kt:3:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 @file:Suppress("SpreadOperator")
2 
3 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
4 
5 import com.android.build.api.dsl.LibraryExtension
6 import com.javiersc.hubdle.extensions._internal.PluginIds

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformAndroid.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.android.build.api.dsl.LibraryExtension
4 import com.javiersc.hubdle.extensions._internal.PluginIds

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformDarwin.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformIOS.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformJs.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformJvm.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformJvmAndAndroid.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformLinux.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformMacOS.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformMinGW.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformNative.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformTvOS.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformWAsm.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformWatchOS.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.state.hubdleState
4 import org.gradle.api.Project

```

### performance, SpreadOperator (3)

In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.

[Documentation](https://detekt.dev/docs/rules/performance#spreadoperator)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformAndroid.kt:36:51
```
In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.
```
```kotlin
33                         androidState.library.publishLibraryVariants.isNotEmpty() -> {
34                             val variants =
35                                 androidState.library.publishLibraryVariants.toTypedArray()
36                             publishLibraryVariants(*variants)
!!                                                   ^ error
37                         }
38                         androidState.library.allLibraryVariants -> publishAllLibraryVariants()
39                     }

```

* hubdle-settings-gradle-plugin/main/kotlin/com/javiersc/hubdle/settings/HubdleSettingsPlugin.kt:62:29
```
In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.
```
```kotlin
59     val autoInclude = hubdleSettings.autoInclude
60 
61     if (autoInclude.isEnabled) {
62         autoInclude.includes(*extractedProjects().toTypedArray())
!!                             ^ error
63         autoInclude.includedBuilds(*extractedBuildProjects().toTypedArray())
64 
65         for (include in autoInclude.includableProjects) {

```

* hubdle-settings-gradle-plugin/main/kotlin/com/javiersc/hubdle/settings/HubdleSettingsPlugin.kt:63:35
```
In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.
```
```kotlin
60 
61     if (autoInclude.isEnabled) {
62         autoInclude.includes(*extractedProjects().toTypedArray())
63         autoInclude.includedBuilds(*extractedBuildProjects().toTypedArray())
!!                                   ^ error
64 
65         for (include in autoInclude.includableProjects) {
66             include(include)

```

### style, ForbiddenComment (6)

Flags a forbidden comment.

[Documentation](https://detekt.dev/docs/rules/style#forbiddencomment)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/binary/compatibility/validator/_internal/configureBinaryCompatibilityValidator.kt:12:1
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
9  import org.gradle.api.Task
10 import org.gradle.kotlin.dsl.the
11 
12 // TODO: change to per project when it is redesigned to support project isolation
!! ^ error
13 internal fun configureBinaryCompatibilityValidator(project: Project) {
14     if (project.hubdleState.config.binaryCompatibilityValidator.isEnabled) {
15         check(project.isRootProject) {

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/application/_internal/configureAndroidApplication.kt:48:13
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
45             defaultConfig.versionName = androidState.application.versionName
46             compileSdk = androidState.compileSdk
47             defaultConfig.minSdk = androidState.minSdk
48             // TODO: namespace = project.calculateAndroidNamespace(androidState.namespace)
!!             ^ error
49 
50             sourceSets.all { set -> set.configDefaultAndroidSourceSets() }
51             project.configureAndroidBuildFeatures(this)

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/library/HubdleKotlinAndroidLibraryExtension.kt:62:5
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
59         the<LibraryExtension>().sourceSets.named("test", action::execute)
60     }
61 
62     // TODO: improve and enable using this docs:
!!     ^ error
63     //  https://developer.android.com/studio/publish-library/configure-pub-variants
64     // @HubdleDslMarker
65     // public fun Project.publishLibraryVariants(vararg names: String) {

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformJvmAndAndroid.kt:11:13
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
8  internal fun configureMultiplatformJvmAndAndroid(project: Project) {
9      if (project.hubdleState.kotlin.multiplatform.jvmAndAndroid.isEnabled) {
10         project.configure<KotlinMultiplatformExtension> {
11             /* TODO: enable when granular metadata for jvm+android is fixed
!!             ^ error
12             val jvmAndAndroidMain = sourceSets.create("jvmAndAndroidMain")
13             val jvmAndAndroidTest = sourceSets.create("jvmAndAndroidTest")
14 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/_internal/configureMultiplatformJvmAndAndroid.kt:25:13
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
22             sourceSets.findByName("androidTest")?.dependsOn(jvmAndAndroidTest)
23             */
24 
25             // TODO: remove when granular metadata for jvm+android is fixed
!!             ^ error
26             val mainKotlin = "jvmAndAndroid/main/kotlin"
27             val mainResources = "jvmAndAndroid/main/resources"
28             val testKotlin = "jvmAndAndroid/test/kotlin"

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/options/PublishingOptions.kt:175:21
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
172             task.doLast {
173                 val publishNonSemver = getBooleanProperty(HubdleProperty.Publishing.nonSemver)
174                 check(isSemver || publishNonSemver) {
175                     // TODO: inject `$version` instead of getting it from the `project`
!!!                     ^ error
176                     """|Only semantic versions can be published (current: $version)
177                        |Use `"-Ppublishing.nonSemver=true"` to force the publication 
178                     """

```

### style, MaxLineLength (1)

Line detected, which is longer than the defined maximum line length in the code style.

[Documentation](https://detekt.dev/docs/rules/style#maxlinelength)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/readme/_internal/configureReadme.kt:22:17
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
19 
20         val projectKey =
21             project.getPropertyOrNull(HubdleProperty.Analysis.projectKey)
22                 ?: "${project.group}:${project.getPropertyOrNull(HubdleProperty.Project.rootProjectDirName) ?: project.rootDir.name}"
!!                 ^ error
23 
24         project.tasks.register<WriteReadmeBadgesTask>(WriteReadmeBadgesTask.name).configure { task
25             ->

```

generated with [detekt version 1.21.0](https://detekt.dev/) on 2022-10-11 17:09:47 UTC
