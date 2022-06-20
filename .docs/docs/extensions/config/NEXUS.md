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
nexus.snapshotRepositoryUrl=...
nexus.url=...
oss.stagingProfileId=...
oss.token=...
oss.user=...
```


Any property can be provided in screaming case too:

```properties
NEXUS_SNAPSHOT_REPOSITORY_URL=...
NEXUS_URL=...
OSS_STAGING_PROFILE_ID=...
OSS_TOKEN=...
OSS_USER=...
```
