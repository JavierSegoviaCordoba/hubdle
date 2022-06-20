# Analysis

Enable `detekt` and `sonarqube` to analyse all projects

## Configuration

It must be configured only in the root project.

### Basic config

```kotlin
hubdle {
    kotlin {
       analysis() 
    }
}
```

- Local properties, environment variables or Gradle properties

```properties
codeAnalysis.sonar.projectKey=com.javiersc.hubdle:hubdle
codeAnalysis.sonar.projectName=hubdle
codeAnalysis.sonar.login=user-sonar-token
codeAnalysis.sonar.host.url=https://sonarcloud.io
codeAnalysis.sonar.organization=javiersc
```

Any property can be provided in screaming case too:

```properties
CODE_ANALYSIS_SONAR_PROJECT_KEY=com.javiersc.hubdle:hubdle
CODE_ANALYSIS_SONAR_PROJECT_NAME=hubdle
CODE_ANALYSIS_SONAR_LOGIN=user-sonar-token
CODE_ANALYSIS_SONAR_HOST=https://sonarcloud.io
CODE_ANALYSIS_SONAR_ORGANIZATION=javiersc
```

### Advanced config

```kotlin
hubdle {
    kotlin {
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
