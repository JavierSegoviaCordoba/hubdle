# Module code-analysis

A custom plugin for code analysis which uses [Detekt](https://github.com/detekt/detekt) and
Sonarqube under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:code-analysis:$version")
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-code-analysis`
}
```

### Usage

Completely configured, and it adds automatically the `detekt.xml` config used by the IDEA plugin, so
the IDEA plugin is enabled by default.

#### Sonarqube configurations

##### Sonar project key

Two options:

- `codeAnalysis.sonar.projectKey=projectKey` (remember to change `projectKey` with your real key)
- Set the project key to match `${group}:{project.name}`, for
  example: `com.javiersc.semver:semantic-versioning-kmp`

##### Sonar login token

As Gradle property:

```properties
codeAnalysis.sonar.login=token
```

As environment variable:

```text
SONAR_TOKEN
```

Remember to change `token` with the real token.

##### Sonar host url

```properties
codeAnalysis.sonar.host.url=url
```

Remember to change `url` with the real token. Default `https://sonarcloud.io`

##### Sonar organization

```properties
codeAnalysis.sonar.organization=org
```

Remember to change `org` with the real token.
