# Nexus

Easy setup to publish to MavenCentral, Snapshot or any Nexus repository

## Configuration

```kotlin
hubdle {
    config {
        nexus()
    }
}
```

- Local properties, environment variables or Gradle properties

```properties
nexus.token=...
nexus.snapshotRepositoryUrl=...
nexus.stagingProfileId=...
nexus.url=...
nexus.user=...
```


Any property can be provided in screaming case too:

```properties
NEXUS_TOKEN=...
NEXUS_SNAPSHOT_REPOSITORY_URL=...
NEXUS_STAGING_PROFILE_ID=...
NEXUS_URL=...
NEXUS_USER=...
```
