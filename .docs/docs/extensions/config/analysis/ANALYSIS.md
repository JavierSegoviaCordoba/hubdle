# Analysis

Enable `detekt` and `sonarqube` to analyse all projects

## Configuration

It must be configured only in the root project.

### Basic config

```kotlin
hubdle {
    config {
        analysis()
    }
}
```

- Local properties, environment variables or Gradle properties

```properties
analysis.sonar.projectKey=com.javiersc.hubdle:hubdle
analysis.sonar.projectName=hubdle
analysis.sonar.login=user-sonar-token
analysis.sonar.host.url=https://sonarcloud.io
analysis.sonar.organization=javiersc
```

Any property can be provided in screaming case too:

```properties
ANALYSIS_SONAR_PROJECT_KEY=com.javiersc.hubdle:hubdle
ANALYSIS_SONAR_PROJECT_NAME=hubdle
ANALYSIS_SONAR_LOGIN=user-sonar-token
ANALYSIS_SONAR_HOST=https://sonarcloud.io
ANALYSIS_SONAR_ORGANIZATION=javiersc
```

### Advanced config

```kotlin
hubdle {
    config {
        analysis {
            isEnabled.set(true)
            ignoreFailure.set(true)
            includes.set(setOf("**/*.kt", "**/*.kts"))
            excludes.set(setOf("**/resources/**", "**/build/**"))

            reports {
                html.set(true)
                sarif.set(true)
                txt.set(true)
                xml.set(true)
            }
        }
    }
}
```
