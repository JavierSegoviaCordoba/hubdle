# Publish from local machine

## Prerequisites

1. Generate key: `gpg --full-generate-key`
2. Check key id: `gpg --list-signatures`
3. Upload to server: `gpg --keyserver hkps://keys.openpgp.org --send-keys [keyId]`
4. Add Nexus user
   - Gradle's property: `oss.user=[user]`
   - Environment variable: `OSS_USER`
5. Add Nexus token
   - Gradle's property: `oss.token=[token]`
   - Environment variable: `OSS_TOKEN`

> You can use GUI utilities:
> - Kleopatra for Windows
> - GPG Suite (GPGTools) for macOS

## Versioning

// TODO

## Publishing

### Snapshot

Version should end with `-SNAPSHOT`

```
./gradlew publishToSonatype -P"reckon.stage"="snapshot" -P"isSnapshot"="true"
```

### Release

```
./gradlew publishToSonatype -P"signing.gnupg.keyName"="[keyId]" -P"signing.gnupg.passphrase"="[passphrase]"
```
