# Module nexus

A custom plugin for publishing to MavenCentral which uses
[publish-plugin](https://github.com/gradle-nexus/publish-plugin) under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:nexus:$version")
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-nexus`
}
```

### Usage

Just apply it and add the next Gradle properties:

- `oss.user`: Copy/generate it from
  [the Nexus profile](https://oss.sonatype.org/#profile;User%20Token)
- `oss.token`: Copy/generate it from
  [the Nexus profile](https://oss.sonatype.org/#profile;User%20Token)
- `oss.stagingProfileId`: Visit [staging profiles](https://oss.sonatype.org/#stagingProfiles) select
  your profile from the list and copy the code after the `;`, for example, the code from this url
  `https://oss.sonatype.org/#stagingProfiles;32gf35h34654` is `32gf35h34654`

Environment variables can be used instead of Gradle properties:

- `OSS_USER`
- `OSS_TOKEN`
- `OSS_STAGING_PROFILE_ID`
