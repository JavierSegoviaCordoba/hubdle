![Kotlin version](https://img.shields.io/badge/kotlin-1.6.21-blueviolet?logo=kotlin&logoColor=white)
[![MavenCentral](https://img.shields.io/maven-central/v/com.javiersc.hubdle/hubdle-gradle-plugin?label=MavenCentral)](https://repo1.maven.org/maven2/com/javiersc/hubdle/hubdle-gradle-plugin/)
[![Snapshot](https://img.shields.io/nexus/s/com.javiersc.hubdle/hubdle-gradle-plugin?server=https%3A%2F%2Foss.sonatype.org%2F&label=Snapshot)](https://oss.sonatype.org/content/repositories/snapshots/com/javiersc/hubdle/hubdle-gradle-plugin/)

[![Build](https://img.shields.io/github/workflow/status/JavierSegoviaCordoba/hubdle/build-kotlin?label=Build&logo=GitHub)](https://github.com/JavierSegoviaCordoba/hubdle/tree/main)
[![Coverage](https://img.shields.io/sonar/coverage/com.javiersc.gradle:hubdle-gradle-plugin?label=Coverage&logo=SonarCloud&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=com.javiersc.gradle:hubdle-gradle-plugin)
[![Quality](https://img.shields.io/sonar/quality_gate/com.javiersc.gradle:hubdle-gradle-plugin?label=Quality&logo=SonarCloud&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=com.javiersc.gradle:hubdle-gradle-plugin)
[![Tech debt](https://img.shields.io/sonar/tech_debt/com.javiersc.gradle:hubdle-gradle-plugin?label=Tech%20debt&logo=SonarCloud&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=com.javiersc.gradle:hubdle-gradle-plugin)

# Hubdle

## Usage

```kotlin
plugins {
    id("com.javiersc.hubdle") version "$version"
}

// basic config to get a Kotlin jvm project with coroutines
hubdle {
    kotlin {
        jvm {
            features {
                coroutines()                
            }
        }
    }
}
```

## Configurations

Check [extensions](https://hubdle.javiersc.com/)

## License

```
Copyright 2022 Javier Segovia Córdoba

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
