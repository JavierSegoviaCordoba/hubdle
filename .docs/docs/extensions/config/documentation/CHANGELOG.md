# Changelog

Manage the changelog easily

## Configuration

```kotlin
hubdle {
    config {
        documentation {
            changelog()
        }
    }
}
```

## Patch the changelog

```shell
./gradlew patchChangelog
```

## Add an item to the changelog

```shell
./gradlew addChangelogItem --added "some new addition"
```

```shell
./gradlew addChangelogItem --changed "some change"
```

```shell
./gradlew addChangelogItem --removed "something because it is no longer necessary"
```

## Format the changelog

```shell
./gradlew applyFormatChangelog
```

## Merge the changelog

Merge all non-final related versions into the final one 

```shell
./gradlew mergeChangelog
```
