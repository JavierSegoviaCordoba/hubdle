# detekt

## Metrics

* 1,293 number of properties

* 541 number of functions

* 221 number of classes

* 73 number of packages

* 205 number of kt files

## Complexity Report

* 12,705 lines of code (loc)

* 10,449 source lines of code (sloc)

* 6,893 logical lines of code (lloc)

* 91 comment lines of code (cloc)

* 917 cyclomatic complexity (mcc)

* 486 cognitive complexity

* 89 number of total code smells

* 0% comment source ratio

* 133 mcc per 1,000 lloc

* 12 code smells per 1,000 lloc

## Findings (89)

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

### complexity, CyclomaticComplexMethod (1)

Prefer splitting up complex methods into smaller, easier to test methods.

[Documentation](https://detekt.dev/docs/rules/complexity#cyclomaticcomplexmethod)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/features/HubdleKotlinAndroidBuildFeaturesExtension.kt:45:26
```
The function defaultConfiguration appears to be too complex based on Cyclomatic Complexity (complexity: 15). Defined complexity threshold for methods is set to '15'
```
```kotlin
42     public val shaders: Property<Boolean?> = property { null }
43     public val viewBinding: Property<Boolean?> = property { null }
44 
45     override fun Project.defaultConfiguration() {
!!                          ^ error
46         configurable {
47             val feats = this@HubdleKotlinAndroidBuildFeaturesExtension
48             configureAndroidCommonExtension {

```

### complexity, LongMethod (4)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* hubdle-gradle-plugin/build.gradle.kts:170:13
```
The function buildHubdleDependencies is too long (71). The maximum length is 60.
```
```kotlin
167         }
168 }
169 
170 fun Project.buildHubdleDependencies() {
!!!             ^ error
171     buildDir.resolve("generated/main/kotlin/hubdle_dependencies.kt").apply {
172         parentFile.mkdirs()
173         createNewFile()

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/site/HubdleConfigDocumentationSiteExtension.kt:61:26
```
The function defaultConfiguration is too long (64). The maximum length is 60.
```
```kotlin
58         userConfigurable { action.execute(the()) }
59     }
60 
61     override fun Project.defaultConfiguration() {
!!                          ^ error
62         val project = this
63         applicablePlugin(
64             priority = Priority.P4,

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformAppleExtension.kt:78:26
```
The function defaultConfiguration is too long (94). The maximum length is 60.
```
```kotlin
75         watchos.enableAndExecute(action)
76     }
77 
78     override fun Project.defaultConfiguration() {
!!                          ^ error
79         configurable {
80             if (allEnabled.get()) {
81                 ios.allEnabled()

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformNativeExtension.kt:43:26
```
The function defaultConfiguration is too long (125). The maximum length is 60.
```
```kotlin
40         allEnabled.set(value)
41     }
42 
43     override fun Project.defaultConfiguration() {
!!                          ^ error
44         configurable {
45             if (allEnabled.get()) {
46                 hubdleKotlinMultiplatform.apple.allEnabled()

```

### complexity, NestedBlockDepth (1)

Excessive nesting leads to hidden complexity. Prefer extracting code to make it easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#nestedblockdepth)

* hubdle-gradle-plugin/build.gradle.kts:170:13
```
Function buildHubdleDependencies is nested too deeply.
```
```kotlin
167         }
168 }
169 
170 fun Project.buildHubdleDependencies() {
!!!             ^ error
171     buildDir.resolve("generated/main/kotlin/hubdle_dependencies.kt").apply {
172         parentFile.mkdirs()
173         createNewFile()

```

### complexity, TooManyFunctions (5)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/withPluginExtensions.kt:1:1
```
File '/home/runner/work/hubdle/hubdle/hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/withPluginExtensions.kt' with '19' functions detected. Defined threshold inside files is set to '11'
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import com.android.build.api.dsl.ApplicationExtension
4 import com.android.build.api.dsl.LibraryExtension

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/apis/HubdleEnableableExtension.kt:22:23
```
Class 'HubdleEnableableExtension' with '11' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
19 import org.gradle.kotlin.dsl.configure
20 import org.gradle.kotlin.dsl.the
21 
22 public abstract class HubdleEnableableExtension(
!!                       ^ error
23     internal open val project: Project,
24 ) : BaseHubdleEnableableExtension {
25 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/HubdleConfigExtension.kt:27:19
```
Class 'HubdleConfigExtension' with '11' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
24 import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
25 
26 @HubdleDslMarker
27 public open class HubdleConfigExtension
!!                   ^ error
28 @Inject
29 constructor(
30     project: Project,

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/language/settings/HubdleConfigLanguageSettingsExtension.kt:15:19
```
Class 'HubdleConfigLanguageSettingsExtension' with '11' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
12 import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
13 import org.jetbrains.kotlin.gradle.plugin.LanguageSettingsBuilder
14 
15 public open class HubdleConfigLanguageSettingsExtension
!!                   ^ error
16 @Inject
17 constructor(
18     project: Project,

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/HubdleKotlinMultiplatformExtension.kt:39:19
```
Class 'HubdleKotlinMultiplatformExtension' with '14' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
36 import org.jetbrains.kotlin.gradle.plugin.KotlinTargetPreset
37 
38 @HubdleDslMarker
39 public open class HubdleKotlinMultiplatformExtension
!!                   ^ error
40 @Inject
41 constructor(
42     project: Project,

```

### empty-blocks, EmptyFunctionBlock (1)

Empty block of code detected. As they serve no purpose they should be removed.

[Documentation](https://detekt.dev/docs/rules/empty-blocks#emptyfunctionblock)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformCommonExtension.kt:31:49
```
This empty block of code can be removed.
```
```kotlin
28 
29     public override val targetName: String = "common"
30 
31     override fun Project.defaultConfiguration() {}
!!                                                 ^ error
32 }
33 

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

### naming, InvalidPackageDeclaration (29)

Kotlin source files should be stored in the directory corresponding to its package statement.

[Documentation](https://detekt.dev/docs/rules/naming#invalidpackagedeclaration)

* sandbox/consumer-kotlin-android/main/kotlin/Main.kt:1:1
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

* sandbox/consumer-kotlin-multiplatform/android/main/kotlin/Platform.kt:1:1
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

* sandbox/consumer-kotlin-multiplatform/common/main/kotlin/Platform.kt:1:1
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

* sandbox/consumer-kotlin-multiplatform/jvm/main/kotlin/Platform.kt:1:1
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

### naming, MemberNameEqualsClassName (1)

A member should not be given the same name as its parent class or object.

[Documentation](https://detekt.dev/docs/rules/naming#membernameequalsclassname)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/changelog/_internal/Changelog.kt:41:9
```
A member is named after the class. This might result in confusion. Either rename the member or change it to a constructor.
```
```kotlin
38 
39     internal class Version(val value: String, val groups: List<Group>) {
40 
41         val version: String
!!         ^ error
42             get() =
43                 if (value.contains("[")) value.substringAfter("[").substringBefore("]")
44                 else

```

### naming, PackageNaming (22)

Package names should match the naming convention set in the configuration.

[Documentation](https://detekt.dev/docs/rules/naming#packagenaming)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/CatalogDependencies.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import com.javiersc.gradle.extensions.version.catalogs.catalogNamesWithLibsAtFirst
4 import com.javiersc.gradle.extensions.version.catalogs.getLibraries

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/HubdleState.kt:3:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 @file:Suppress("MagicNumber")
2 
3 package com.javiersc.hubdle.extensions._internal
! ^ error
4 
5 import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
6 import com.javiersc.hubdle.extensions._internal.Configurable.Priority

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/PluginId.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 internal enum class PluginId(val id: String) {
4     AdarshrTestLogger("com.adarshr.test-logger"),

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/SourceSetsConstant.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 internal const val ANDROID_MAIN = "androidMain"
4 internal const val MAIN = "main"

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/checkCompatibility.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions.kotlin.android.application.hubdleAndroidApplication
4 import com.javiersc.hubdle.extensions.kotlin.android.hubdleAndroid

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/configureDependencies.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
4 import com.javiersc.hubdle.extensions.config.testing.HubdleConfigTestingExtension.Options

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/hasPluginExensions.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import org.gradle.api.Project
4 import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/hubdleExtension.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions.apis.BaseHubdleExtension as BaseHubdleExt
4 import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension as HubdleEnableableExt

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/projectExtensions.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import org.gradle.api.Project
4 import org.gradle.api.provider.ListProperty

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/withPluginExtensions.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions._internal
! ^ error
2 
3 import com.android.build.api.dsl.ApplicationExtension
4 import com.android.build.api.dsl.LibraryExtension

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/android/_internal/AndroidCommonExtension.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.android._internal
! ^ error
2 
3 import com.android.build.api.dsl.ApplicationExtension
4 import com.android.build.api.dsl.CommonExtension

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/_internal/createAndConfigureHubdleConfigExtensions.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.HubdleState
4 import com.javiersc.hubdle.extensions.config.HubdleConfigExtension

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
5 import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Reference
6 import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version

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

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/publishing/_internal/configureMavenPublishing.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.config.publishing._internal
! ^ error
2 
3 import com.javiersc.gradle.project.extensions.isNotSnapshot
4 import com.javiersc.gradle.properties.extensions.getBooleanProperty

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/_internal/configureKotlinSourceSets.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin._internal
! ^ error
2 
3 import com.javiersc.gradle.tasks.extensions.namedLazily
4 import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/_internal/createAndConfigureHubdleKotlinExtensions.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin._internal
! ^ error
2 
3 import com.javiersc.hubdle.extensions._internal.HubdleState
4 import com.javiersc.hubdle.extensions.kotlin.HubdleKotlinExtension

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/_internal/forKotlinSets.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin._internal
! ^ error
2 
3 import org.gradle.api.Project
4 import org.gradle.kotlin.dsl.configure

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/_internal/calculateAndroidNamespace.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle.extensions.kotlin.android._internal
! ^ error
2 
3 import com.android.build.api.dsl.ApplicationExtension
4 import com.android.build.api.dsl.LibraryExtension

```

* hubdle-gradle-plugin/test/kotlin/com/javiersc/hubdle/_utils/commitAndCheckout.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle._utils
! ^ error
2 
3 import java.io.File
4 import org.eclipse.jgit.api.Git

```

* hubdle-gradle-plugin/test/kotlin/com/javiersc/hubdle/_utils/resourceFile.kt:1:1
```
Package name should match the pattern: [a-z]+(\.[a-z][A-Za-z0-9]*)*
```
```kotlin
1 package com.javiersc.hubdle._utils
! ^ error
2 
3 import java.io.File
4 

```

### naming, TopLevelPropertyNaming (1)

Top level property names should follow the naming convention set in the projects configuration.

[Documentation](https://detekt.dev/docs/rules/naming#toplevelpropertynaming)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/PluginId.kt:35:19
```
Top level constant names should match the pattern: [A-Z][_A-Z0-9]*
```
```kotlin
32     override fun toString(): String = id
33 }
34 
35 private const val KotlinBinaryCompatibilityValidator =
!!                   ^ error
36     "org.jetbrains.kotlinx.binary-compatibility-validator"
37 

```

### performance, SpreadOperator (3)

In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.

[Documentation](https://detekt.dev/docs/rules/performance#spreadoperator)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformAndroidExtension.kt:94:47
```
In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.
```
```kotlin
91                 val publishAllVariants = hubdleAndroid.publishAllLibraryVariants.get()
92                 android {
93                     if (publishVariants.isNotEmpty() && !publishAllVariants) {
94                         publishLibraryVariants(*publishVariants)
!!                                               ^ error
95                     }
96                     if (publishAllVariants) {
97                         publishAllLibraryVariants()

```

* hubdle-settings-gradle-plugin/main/kotlin/com/javiersc/hubdle/settings/HubdleSettingsPlugin.kt:62:29
```
In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.
```
```kotlin
59     val autoInclude = hubdleSettings.autoInclude
60 
61     if (autoInclude.isEnabled.get()) {
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
61     if (autoInclude.isEnabled.get()) {
62         autoInclude.includes(*extractedProjects().toTypedArray())
63         autoInclude.includedBuilds(*extractedBuildProjects().toTypedArray())
!!                                   ^ error
64 
65         for (include in autoInclude.includableProjects) {
66             include(include)

```

### style, ForbiddenComment (10)

Flags a forbidden comment.

[Documentation](https://detekt.dev/docs/rules/style#forbiddencomment)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/projectExtensions.kt:11:1
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
8  import org.gradle.kotlin.dsl.property
9  import org.gradle.kotlin.dsl.setProperty
10 
11 // TODO: Extract to Gradle Extensions libraries
!! ^ error
12 internal inline fun <reified T> Project.property(crossinline block: Project.() -> T): Property<T> =
13     objects.property<T>().convention(project.provider { block(this) })
14 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/projectExtensions.kt:15:1
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
12 internal inline fun <reified T> Project.property(crossinline block: Project.() -> T): Property<T> =
13     objects.property<T>().convention(project.provider { block(this) })
14 
15 // TODO: Extract to Gradle Extensions libraries
!! ^ error
16 internal inline fun <reified T> Project.listProperty(
17     crossinline block: Project.() -> List<T>
18 ): ListProperty<T> = objects.listProperty<T>().convention(project.provider { block(this) })

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/projectExtensions.kt:20:1
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
17     crossinline block: Project.() -> List<T>
18 ): ListProperty<T> = objects.listProperty<T>().convention(project.provider { block(this) })
19 
20 // TODO: Extract to Gradle Extensions libraries
!! ^ error
21 internal inline fun <reified T> Project.setProperty(
22     crossinline block: Project.() -> Set<T>
23 ): SetProperty<T> = objects.setProperty<T>().convention(project.provider { block(this) })

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/analysis/HubdleConfigAnalysisExtension.kt:93:13
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
90         )
91 
92         configurable {
93             // TODO: Fix do this per project
!!             ^ error
94             check(project.isRootProject) {
95                 """Hubdle `analysis()` must be only configured in the root project"""
96             }

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/documentation/site/HubdleConfigDocumentationSiteExtension.kt:99:13
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
96                  publish.docPath = "_site"
97              }
98  
99              // TODO: fix project isolation
!!              ^ error
100             allprojects { allproject ->
101                 allproject.afterEvaluate { allprojectAfterEvaluate ->
102                     if (allprojectAfterEvaluate.hasKotlinGradlePlugin) {

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/publishing/_internal/configureMavenPublishing.kt:116:25
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
113                 task.doLast {
114                     val publishNonSemver = getBooleanProperty(HubdleProperty.Publishing.nonSemver)
115                     check(isSemver || publishNonSemver) {
116                         // TODO: inject `$version` instead of getting it from the `project`
!!!                         ^ error
117                         """|Only semantic versions can be published (current: $version)
118                            |Use `"-Ppublishing.nonSemver=true"` to force the publication 
119                         """

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/HubdleKotlinAndroidExtension.kt:20:1
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
17 import org.gradle.api.Project
18 import org.gradle.api.provider.Property
19 
20 // TODO: transform into `HubdleConfigurableExtension`
!! ^ error
21 @HubdleDslMarker
22 public open class HubdleKotlinAndroidExtension
23 @Inject

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/library/HubdleKotlinAndroidLibraryExtension.kt:61:5
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
58         }
59     }
60 
61     // TODO: improve and enable using this docs:
!!     ^ error
62     //  https://developer.android.com/studio/publish-library/configure-pub-variants
63     // @HubdleDslMarker
64     // public fun publishLibraryVariants(vararg names: String) {

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformJvmAndAndroidExtension.kt:36:17
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
33     override fun Project.defaultConfiguration() {
34         configurable {
35             configure<KotlinMultiplatformExtension> {
36                 /* TODO: enable when granular metadata for jvm+android is fixed
!!                 ^ error
37                 val jvmAndAndroidMain = sourceSets.create("jvmAndAndroidMain")
38                 val jvmAndAndroidTest = sourceSets.create("jvmAndAndroidTest")
39 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformJvmAndAndroidExtension.kt:50:17
```
This comment contains 'TODO:' that has been defined as forbidden in detekt.
```
```kotlin
47                 sourceSets.findByName("androidTest")?.dependsOn(jvmAndAndroidTest)
48                 */
49 
50                 // TODO: remove when granular metadata for jvm+android is fixed
!!                 ^ error
51                 val mainKotlin = "jvmAndAndroid/main/kotlin"
52                 val mainResources = "jvmAndAndroid/main/resources"
53                 val testKotlin = "jvmAndAndroid/test/kotlin"

```

### style, MagicNumber (6)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/HubdleKotlinAndroidExtension.kt:35:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
32 
33     public val namespace: Property<String?> = property { null }
34 
35     public val minSdk: Property<Int> = property { 23 }
!!                                                   ^ error
36 
37     public val compileSdk: Property<Int> = property { 33 }
38 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/HubdleKotlinAndroidExtension.kt:37:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
34 
35     public val minSdk: Property<Int> = property { 23 }
36 
37     public val compileSdk: Property<Int> = property { 33 }
!!                                                       ^ error
38 
39     public val targetSdk: Property<Int> = property { 33 }
40 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/android/HubdleKotlinAndroidExtension.kt:39:54
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
36 
37     public val compileSdk: Property<Int> = property { 33 }
38 
39     public val targetSdk: Property<Int> = property { 33 }
!!                                                      ^ error
40 
41     public val buildFeatures: HubdleKotlinAndroidBuildFeaturesExtension
42         get() = getHubdleExtension()

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformAndroidExtension.kt:45:51
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
42 
43     public val namespace: Property<String?> = property { null }
44 
45     public val minSdk: Property<Int> = property { 23 }
!!                                                   ^ error
46 
47     public val compileSdk: Property<Int> = property { 33 }
48 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformAndroidExtension.kt:47:55
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
44 
45     public val minSdk: Property<Int> = property { 23 }
46 
47     public val compileSdk: Property<Int> = property { 33 }
!!                                                       ^ error
48 
49     public val targetSdk: Property<Int> = property { 33 }
50 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/multiplatform/targets/HubdleKotlinMultiplatformAndroidExtension.kt:49:54
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
46 
47     public val compileSdk: Property<Int> = property { 33 }
48 
49     public val targetSdk: Property<Int> = property { 33 }
!!                                                      ^ error
50 
51     public val buildFeatures: HubdleKotlinAndroidBuildFeaturesExtension
52         get() = getHubdleExtension()

```

### style, MaxLineLength (1)

Line detected, which is longer than the defined maximum line length in the code style.

[Documentation](https://detekt.dev/docs/rules/style#maxlinelength)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/_internal/HubdleState.kt:139:21
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
136                 }
137 
138                 override fun toString(): String =
139                     "ApplicablePlugin(pluginId=$pluginId, isEnabled=${isEnabled.get()}, priority=$priority, scope=$scope)"
!!!                     ^ error
140             }
141     }
142 }

```

### style, UnusedPrivateMember (2)

Private member is unused and should be removed.

[Documentation](https://detekt.dev/docs/rules/style#unusedprivatemember)

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/config/binary/compatibility/validator/HubdleConfigBinaryCompatibilityValidatorExtension.kt:71:21
```
Private property `apiValidationExtension` is unused.
```
```kotlin
68     HubdleConfigBinaryCompatibilityValidatorExtension
69     get() = getHubdleExtension()
70 
71 private val Project.apiValidationExtension: ApiValidationExtension
!!                     ^ error
72     get() = getHubdleExtension()
73 

```

* hubdle-gradle-plugin/main/kotlin/com/javiersc/hubdle/extensions/kotlin/_internal/configureKotlinSourceSets.kt:219:26
```
Private property `testCompilation` is unused.
```
```kotlin
216 private val KotlinTarget.mainCompilation: KotlinCompilation<KotlinCommonOptions>?
217     get() = compilation(COMMON_MAIN) ?: compilation(MAIN)
218 
219 private val KotlinTarget.testCompilation: KotlinCompilation<KotlinCommonOptions>?
!!!                          ^ error
220     get() = compilation(COMMON_TEST) ?: compilation(TEST)
221 
222 private val KotlinTarget.testFixturesCompilation: KotlinCompilation<KotlinCommonOptions>?

```

generated with [detekt version 1.22.0](https://detekt.dev/) on 2023-02-08 16:11:01 UTC
