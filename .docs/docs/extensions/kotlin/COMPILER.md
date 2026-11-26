# Kotlin compiler plugins

Kotlin compiler plugins and libraries for compiler plugins, built with the
[Kotlin Compiler Plugin DevKit](https://github.com/Kotlin/compiler-plugin-dev-kit).

The DevKit compiles and tests the project against several Kotlin compiler versions, the CLI ones
and the ones bundled in IntelliJ IDEA, and publishes one jar with the classes of every version.

## Overview

- compilerLibrary
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - `cliVersions(min: String, max: String? = null)`
    - `ideaVersionsEnabled(value: Boolean)` // true
    - `ideaVersions(min: String, max: Int? = null, includeRc: Boolean = true, includeEap: Boolean = true)` // 263
    - `pluginPackage(value: String)` // the project group
    - `main(action: Action<KotlinSourceSet>)`
    - `test(action: Action<KotlinSourceSet>)`
    - `devKit(action: Action<DevKitCompilerConfigExtension>)`
    - `kotlin(action: Action<KotlinMultiplatformExtension>)`
- compilerPlugin
    - everything in `compilerLibrary`
    - `componentRegistrar(value: String)`
    - `commandLineProcessor(value: String)`
- gradle
    - plugin
        - compilerPluginDevKit
            - `isEnabled: Property<Boolean>` // false
            - `enabled(value: Boolean = true)`
            - `compilerPlugin(value: String)`
            - `compilerPlugin(project: ProjectDependency)`
            - `cliVersions(min: String, max: String? = null)`
            - `pluginPackage(value: String)` // the project group
            - `devKit(action: Action<GradlePluginConfigExtension>)`

`compilerLibrary` is a library that compiler plugins use, for example shared compiler utilities.
`compilerPlugin` is the compiler plugin itself: it also has the entry points (`componentRegistrar`
and `commandLineProcessor`) and the generated compiler tests.

A project can use only one of `compilerLibrary`, `compilerPlugin`, `jvm`, `multiplatform` or
`android`.

## Settings

The DevKit needs its settings plugin. Enable it with a Gradle property in `gradle.properties`:

```properties
hubdle.kotlin.compiler.plugin.devkit=true
```

It is a Gradle property, not a `hubdleSettings` option, because the DevKit settings plugin must be
applied before the settings are evaluated. It adds the Kotlin `bootstrap`, `dev` and
`intellij-dependencies` repositories.

## Versions

- `cliVersions(min, max)` adds every stable Kotlin version between `min` and `max`, and the Beta and
  RC versions of the newest one, the release in preparation. Without it, the project only uses the
  Kotlin Gradle Plugin version of the build.
- `ideaVersions(min, max, includeRc, includeEap)` adds the Kotlin compilers bundled in the IntelliJ
  IDEA builds between `min` and `max`. The default is IntelliJ IDEA 2026.3 (`263`) and later, RC and
  EAP included. Compiling against the IDE compilers catches the internal compiler API breaks that
  otherwise only show up inside the IDE. `ideaVersionsEnabled(false)` turns it off.

Each version is a Kotlin target named `k` + the version, for example `k2421` for `2.4.21` and
`k250_ij263_18` for `2.5.0-ij263-18`.

## Layout

Hubdle maps the DevKit source sets to its own layout, without the `src` directory:

| DevKit source set                         | Directory                                     |
|-------------------------------------------|-----------------------------------------------|
| `commonMain`                              | `common/main/kotlin`, `common/main/resources` |
| `commonTestFixtures`                      | `common/testFixtures/kotlin`                  |
| `commonTest`                              | `common/test/kotlin`, `common/test/data`      |
| `<group>Main`, for example `pre23DevMain` | `<group>/main/kotlin`                         |
| `<group>Test`, for example `jsTest`       | `<group>/test/kotlin`, `<group>/test/data`    |
| `generateTests`                           | `common/generateTests/kotlin`                 |
| `generateTests<Group>`                    | `<group>/generateTests/kotlin`                |

- The test source sets use `data` instead of `resources`: that is where the test data and the dumps
  live.
- The source sets of each version, for example `k2421Main`, have no directory: put the code for
  some versions in a group, for example `pre23Dev/main/kotlin`.

```
common/
    main/kotlin/            # the compiler plugin
    testFixtures/kotlin/    # the abstract test classes
    test/data/box/          # the test data and its dumps
    generateTests/kotlin/   # the test generator, GenerateTests.kt
```

## Tests

`compilerPlugin` generates the compiler tests from the test data with the test generator, the
`<pluginPackage>.GenerateTestsKt` class in `common/generateTests/kotlin`:

```kotlin
fun main(): Unit = generateDevKitTestsWithJUnit5 {
    testClass<AbstractJvmBoxTest> { model("box") }
}
```

- `allTests` runs the tests against every version.
- `-PupdateTestData=true` writes the dumps that differ.
- `deleteCompilerTextTestFiles` deletes the dumps (`.txt` and `.patch`, classified dumps such as
  `foo.2420.fir.txt` included) of every test data directory, so the next test run writes them
  again. It keeps the files with `TODO` or `DISABLED` in their path.

## Gradle plugin

The Gradle plugin that applies the compiler plugin to the users' projects is a regular Hubdle
Gradle plugin, `kotlin { jvm() }` with `gradle { plugin { } }`, plus `compilerPluginDevKit { }`:

```kotlin
hubdle {
    kotlin {
        jvm()
    }

    gradle {
        plugin {
            gradlePlugin {
                plugins {
                    create("example") {
                        id = "com.example"
                        implementationClass = "com.example.ExampleGradlePlugin"
                    }
                }
            }

            compilerPluginDevKit {
                compilerPlugin(projects.exampleCompilerPlugin)
            }
        }
    }
}
```

- `compilerPlugin` is the compiler plugin that this Gradle plugin applies: a project, the artifact
  of a project with the same group and version, or the coordinates (`com.example:example:1.0.0`).
- The DevKit generates a `PluginInfo` object, in the `pluginPackage` package, with the plugin ID
  and the compiler plugin artifact, `PluginInfo.PLUGIN_ID` and `PluginInfo.PLUGIN_ARTIFACT`.
- The functional tests are in `testFunctional/kotlin`, as in any other Hubdle project. Don't use
  `testFunctional { }`: the DevKit creates them, in the `functionalTest` source set.
- `<version>FunctionalTest` runs the functional tests against a Kotlin version, for example
  `k2421FunctionalTest`, and `allVersionedFunctionalTests` runs all of them. `cliVersions(min, max)`
  adds versions, and without it the DevKit only uses the Kotlin Gradle Plugin version of the build.
- The DevKit publishes the compiler plugin and the Gradle plugin to a local repository before the
  functional tests run.
- The JVM version defaults to Java 17, the version that the DevKit runtime needs.

## Publishing

With `config { publishing() }`, the project publishes a Kotlin Multiplatform root publication, and
one publication for the first target with the classes of every version in a multi-release jar.

## Configuration

### Basic config

```kotlin
hubdle {
    kotlin {
        compilerPlugin {
            componentRegistrar("com.example.ExampleComponentRegistrar")
            commandLineProcessor("com.example.ExampleCommandLineProcessor")
        }
    }
}
```

### Advanced config

```kotlin
hubdle {
    config {
        explicitApi()
        publishing()
    }

    kotlin {
        compilerPlugin {
            cliVersions(min = "2.4.0")
            ideaVersions(min = "263")
            pluginPackage("com.example")

            componentRegistrar("com.example.ExampleComponentRegistrar")
            commandLineProcessor("com.example.ExampleCommandLineProcessor")

            main {
                dependencies {
                    implementation(libs.some.lib)
                }
            }

            devKit {
                testDataLibraries {
                    common(project(":example-annotations"))
                }
            }
        }
    }
}
```
