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
           isEnabled = true
           ignoreFailure = true
           includes = mutableListOf("**/*.kt", "**/*.kts")
           excludes = mutableListOf("**/resources/**", "**/build/**")
           
           reports {
               html = true
               sarif = true
               txt = true
               xml = true
           }
       }
    }
}
```
