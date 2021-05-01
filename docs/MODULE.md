# Module docs

A custom plugin for generating a website, including API docs or adding the changelog.
It uses [Dokka](https://github.com/Kotlin/dokka) and
[mkdocs](https://github.com/xvik/gradle-mkdocs-plugin) under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:docs:$version")
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-docs`
}
```

### Usage

The next task will generate Dokka docs and the website:

```shell
./gradlew buildDocs
```

If `.docs` directory doesn't exist in the project, it is created with a few files:
- `.docs/mkdocs.yml`: mkdocs config.
- `.docs/docs/css/all.css`: extra css config.
- `.docs/docs/assets/empty.file`: Remove this file, add logo/favicon and so on.
  
The only config necessary is adding custom navigations to `mkdocs.yml` and whatever necessary page.
Additionally, Kotlin API docs from Dokka and Changelog are added to the website automatically.
Changelog is added if CHANGELOG.md file exists in the root dir.

API docs directories are based on the version:
- `/api/`: shortcut to the latest version.
- `/api/versions/1.0.0/`, `/api/versions/1.0.1/` and so on. Avoid cleaning the branch so all version
  are saved.
- `/api/snapshot/`: If version ends with `-SNAPSHOT` 

#### Ignore projects

- Avoid certain modules can be published

```kotlin
dokkaHtmlMultiModule {
    removeChildTasks(
        listOf(
            project(":subprojectA"),
            project(":subprojectB")
        )
    )
}
```
