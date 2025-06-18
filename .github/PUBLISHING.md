# Publishing

## Prerequisites

> Both methods can be mixed because if a variable is not found, it will try to get it using the
> other method.

Add all POM properties:

```properties
pom.name=...
pom.description=...
pom.url=...
pom.license.name=...
pom.license.url=...
pom.developer.id=...
pom.developer.name=...
pom.developer.email=...
pom.scm.url=...
pom.scm.connection=...
pom.scm.developerConnection=...
```

### MavenCentral and signing properties using Gradle project properties

> It is recommended to set the next properties in the `$home/.gradle/gradle.properties` file instead
> of using the project `gradle.properties`.
>
> Another alternative is setting them via CLI, i.e.:
> `./gradlew "-Psigning.gnupg.passphrase=[keyName]"`.

1. Generate key: `gpg --full-generate-key`
2. Check key name: `gpg --list-signatures`
3. Upload to server: `gpg --keyserver hkps://keys.openpgp.org --send-keys [keyName]`
4. Show the private key: `gpg --armor --export-secret-keys [keyName]`
5. Add Nexus user: `mavenCentralUsername=[user]`
6. Add Nexus token: `mavenCentralPassword=[token]`
7. Add the secret key (replace line breaks with `\n`): `signing.gnupg.key=[key]`
8. Add the passphrase: `signing.gnupg.passphrase=[passphrase]`
9. Add KeyId (optional): `signing.keyId=[keyId]`

### MavenCentral and signing properties using environment variables

1. Generate key: `gpg --full-generate-key`
2. Check key name: `gpg --list-signatures`
3. Upload to server: `gpg --keyserver hkps://keys.openpgp.org --send-keys [keyName]`
4. Show the private key: `gpg --armor --export-secret-keys [keyName]`
5. Add Nexus user: `ORG_GRADLE_PROJECT_mavenCentralUsername=[user]`
6. Add Nexus token: `ORG_GRADLE_PROJECT_mavenCentralPassword=[token]`
7. Add the secret key (replace line breaks with `\n`): `SIGNING_GNUPG_KEY=[key]`
    - The line break substitution may be unnecessary in some CI servers like GitHub Actions
8. Add the passphrase: `SIGNING_GNUPG_PASSPHRASE=[passphrase]`
9. Add KeyId (optional): `SIGNING_KEY_ID=[keyId]`

> You can use these utilities:
> - Kleopatra, GUI for Windows
> - GPG Suite (GPGTools), GUI for macOS

## Publish the artifacts

### Snapshot

The version should end with `-SNAPSHOT`

```
./gradlew publishToMavenCentral "-Psemver.stage=snapshot"
```

### Release

```
./gradlew publishToMavenCentral "-Psemver.stage=[stage]" "-Psemver.scope=[scope]"
```
