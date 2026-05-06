# Functional Testing

Use this reference when adding or migrating DCL functional tests.

## Inspect Existing Tests First

Before creating new DCL tests, look for functional tests associated with the feature being migrated.

Search at least:

```text
hubdle-gradle-plugin/testFunctional/kotlin
hubdle-gradle-plugin/testFunctional/resources
```

Use the existing imperative plugin functional tests as behavioral source material. Migrate their
fixtures and assertions when they cover real feature behavior, instead of replacing them with only
feature-enabled log assertions.

## Basic Fixture Shape

Create test class:

```text
testFunctional/kotlin/hubdle/declarative/<feature>/HubdleDeclarativeFeatureNameTest.kt
```

Use:

```kotlin
class HubdleDeclarativeConfigTest : GradleTestKitTest()
```

Create fixtures:

```text
testFunctional/resources/<fixture-group>/basic/settings.gradle.dcl
testFunctional/resources/<fixture-group>/basic/build.gradle.dcl
testFunctional/resources/<fixture-group>/basic/gradle.properties
testFunctional/resources/<fixture-group>/hubdle-disabled/settings.gradle.dcl
testFunctional/resources/<fixture-group>/hubdle-disabled/build.gradle.dcl
testFunctional/resources/<fixture-group>/hubdle-disabled/gradle.properties
```

Use this exact `gradle.properties` content in each functional fixture directory:

```properties
org.gradle.caching=true
org.gradle.configuration-cache=true
org.gradle.parallel=true
org.gradle.unsafe.isolated-projects=true
```

`settings.gradle.dcl`:

```gradle
plugins {
    id("com.javiersc.hubdle.declarative")
}

rootProject.name = "hubdle-feature-sandbox"
```

Basic `build.gradle.dcl`:

```gradle
hubdle {
    config {
        group = "foo-some"
    }
}
```

Disabled `build.gradle.dcl`:

```gradle
hubdle {
    enabled = false

    config {
    }
}
```

Positive test pattern:

```kotlin
gradleTestKitTest("hubdle-config/basic") {
    gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
        .output
        .shouldContainInOrder(
            lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
            lifecycle("config") { "Feature 'config' enabled on ':'" },
        )
}
```

Disabled test pattern:

```kotlin
gradleTestKitTest("hubdle-config/hubdle-disabled") {
    gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
        .output
        .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
        .shouldNotContain(lifecycle("config") { "Feature 'config' enabled on ':'" })
}
```

## Snapshot Fixture Migration

When the original feature has output-based functional tests, copy the relevant original fixture
directories into the DCL feature resources under a feature-specific group, for example:

```text
hubdle-declarative-features/<feature>/testFunctional/resources/<fixture-group>/snapshots/snapshot-1
```

Convert only the Gradle entrypoint needed for the migrated feature to DCL:

```text
settings.gradle.kts -> settings.gradle.dcl
build.gradle.kts -> build.gradle.dcl
```

Keep auxiliary project files, expected output files, `ARGUMENTS.txt`, `gradle.properties`, and
nested sample project build files when they are part of the behavior under test.

Use a parameterized test with `@MethodSource` to discover snapshot directories from resources. Run
each fixture with `withArgumentsFromTXT()` and `build()` so the fixture owns the task arguments.
Assert the observable generated output, not just successful parsing or task execution. If expected
output contains a runtime-dependent version, normalize it in the expected file before comparing.

Example adapted from `HubdleDeclarativeConfigDocumentationReadmeBadgesTest`:

```kotlin
@ParameterizedTest
@MethodSource("provideReadmeBadgeSnapshots")
fun `GIVEN readme badges snapshot WHEN writeReadmeBadges THEN readme is created`(
    snapshot: String
) {
    val sandboxPath = "hubdle-config-documentation-readme-badges/snapshots/$snapshot"
    gradleTestKitTest(sandboxPath = sandboxPath) {
        withArgumentsFromTXT()
        build()

        val expect: File = projectDir.resolve("README.expect.md")
        val actual: File = projectDir.resolve("README.md")

        val actualKotlinVersion =
            actual.readLines().first().substringAfter("kotlin-").substringBefore("-blueviolet")

        expect.apply {
            val updatedText = readText().replace("{VERSION}", actualKotlinVersion)
            writeText(updatedText)
        }
        actual.readText().shouldBe(expect.readText())
    }
}

companion object {

    @JvmStatic
    fun provideReadmeBadgeSnapshots(): Stream<String> {
        val snapshotsFile = resource("hubdle-config-documentation-readme-badges/snapshots")
        val snapshotsPath = Paths.get(snapshotsFile.toURI())
        return Files.list(snapshotsPath)
            .filter(Files::isDirectory)
            .map { it.fileName.toString() }
            .sorted()
    }
}
```

## Test Rules

- Test at least one positive case.
- Test parent-disabled behavior for Hubdle nested features.
- Assert observable behavior, not only parsing.
- Use `LogFixture.lifecycle` for lifecycle log assertions.
- Keep fixtures small.
- When migrating an existing feature, first inspect and reuse/adapt its existing functional tests if
  they exist.
- Add source files only when the feature needs compilation, tests, publications, or plugin-specific
  inputs.
- Add extra tests for defaults, explicit `enabled = false`, plugin application, tasks, generated
  files, publications, or compilation only when the feature owns those semantics.
