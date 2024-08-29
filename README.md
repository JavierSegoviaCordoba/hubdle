![Kotlin version](https://img.shields.io/badge/kotlin-2.0.20-blueviolet?logo=kotlin&logoColor=white)
[![MavenCentral](https://img.shields.io/maven-central/v/com.javiersc.hubdle/hubdle-gradle-plugin?label=MavenCentral)](https://repo1.maven.org/maven2/com/javiersc/hubdle/hubdle-gradle-plugin/)
[![Snapshot](https://img.shields.io/nexus/s/com.javiersc.hubdle/hubdle-gradle-plugin?server=https%3A%2F%2Foss.sonatype.org%2F&label=Snapshot)](https://oss.sonatype.org/content/repositories/snapshots/com/javiersc/hubdle/hubdle-gradle-plugin/)

[![Build](https://img.shields.io/github/actions/workflow/status/JavierSegoviaCordoba/hubdle/build-kotlin.yaml?label=Build&logo=GitHub)](https://github.com/JavierSegoviaCordoba/hubdle/tree/main)
[![Coverage](https://img.shields.io/sonar/coverage/com.javiersc.gradle:hubdle?label=Coverage&logo=SonarCloud&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=com.javiersc.gradle:hubdle)
[![Quality](https://img.shields.io/sonar/quality_gate/com.javiersc.gradle:hubdle?label=Quality&logo=SonarCloud&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=com.javiersc.gradle:hubdle)
[![Tech debt](https://img.shields.io/sonar/tech_debt/com.javiersc.gradle:hubdle?label=Tech%20debt&logo=SonarCloud&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=com.javiersc.gradle:hubdle)

# Hubdle

Project and settings convention plugins which allow to create easily projects with different tools:

- Kotlin projects like JVM, Android, Multiplatform, Gradle plugins, with different features, for
  example Compose,Kotest, Coroutines, Serialization...
- Autoconfiguration of multiple tools like coverage, analysis, lints, formatting, documentation,
  publishing...
- Auto-include projects and version catalogs.

## Usage

```kotlin
plugins {
    id("com.javiersc.hubdle") version "$version"
}

// basic config to get a Kotlin jvm project with coroutines and a custom dependency
hubdle {
    kotlin {
        jvm {
            features {
                coroutines()
            }

            main {
                dependencies {
                    implementation("org.example:example:1.0.0")
                }
            }
        }
    }
}
```

## Configurations

Visit the [website](https://hubdle.javiersc.com/)

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
