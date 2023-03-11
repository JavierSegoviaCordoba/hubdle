# Analysis

## Overview

- analysis
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - `ignoreFailures: Property<Boolean>`
    - `includes: SetProperty<String>` // `"**/*.kt", "**/*.kts"`
    - `excludes: SetProperty<String>` // `"**/resources/**", "**/build/**"`
    - `includes(vararg paths: String)`
    - `excludes(vararg paths: String)`
    - reports
        - `isEnabled: Property<Boolean>` // true
        - `enabled(value: Boolean = true)`
        - `md: Property<Boolean>` // true
        - `html: Property<Boolean>` // true
        - `sarif: Property<Boolean>` // true
        - `txt: Property<Boolean>` // true
        - `xml: Property<Boolean>` // true
    - `detekt(action: Action<DetektExtension>)`

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
