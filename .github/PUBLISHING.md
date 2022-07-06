# Publishing

## Prerequisites

> Both methods can be mixed because if a variable is not found, it will try to get it using the
> another method.

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

### Nexus and signing properties using Gradle project properties

> It is recommended to set the next properties in the `$home/.gradle/gradle.properties` file instead
> of using the project `gradle.properties`.
>
> Another alternative is setting them via CLI, i.e.:
> `./gradlew "-Psigning.gnupg.passphrase=[keyName]"`.

1. Generate key: `gpg --full-generate-key`
2. Check key name: `gpg --list-signatures`
3. Upload to server: `gpg --keyserver hkps://keys.openpgp.org --send-keys [keyName]`
4. Show the private key: `gpg --armor --export-secret-keys [keyName]`
5. Add Nexus user: `nexus.user=[user]`
6. Add Nexus token: `nexus.token=[token]`
7. Add Nexus profileId: `nexus.stagingProfileId=[stagingProfileId]`
    1. Visit this [link](https://oss.sonatype.org/#stagingProfiles), select your profile and copy
       `SOME_NUMBER` from the url `https://oss.sonatype.org/#stagingProfiles;SOME_NUMBERS`
8. Add the secret key (replace line breaks with `\n`): `signing.gnupg.key=[key]`
9. Add the passphrase: `signing.gnupg.passphrase=[passphrase]`
10. Add KeyId (optional): `signing.keyId=[keyId]`

### Nexus and signing properties using environment variables

1. Generate key: `gpg --full-generate-key`
2. Check key name: `gpg --list-signatures`
3. Upload to server: `gpg --keyserver hkps://keys.openpgp.org --send-keys [keyName]`
4. Show the private key: `gpg --armor --export-secret-keys [keyName]`
5. Add Nexus user: `NEXUS_USER=[user]`
6. Add Nexus token: `NEXUS_TOKEN=[token]`
7. Add Nexus profileId: `NEXUX_STAGING_PROFILE_ID=[stagingProfileId]`
    - Visit this [link](https://oss.sonatype.org/#stagingProfiles), select your profile and copy
       `SOME_NUMBER` from the url `https://oss.sonatype.org/#stagingProfiles;SOME_NUMBERS`
8. Add the secret key (replace line breaks with `\n`): `SIGNING_GNUPG_KEY=[key]`
   - The line break substitution may be unnecessary in some CI servers like GitHub Actions
9. Add the passphrase: `SIGNING_GNUPG_PASSPHRASE=[passphrase]`
10. Add KeyId (optional): `SIGNING_KEY_ID=[keyId]`

> You can use these utilities:
> - Kleopatra, GUI for Windows
> - GPG Suite (GPGTools), GUI for macOS

## Publish the artifacts

### Snapshot

The version should end with `-SNAPSHOT`

```
./gradlew publishToSonatype "-Psemver.stage=snapshot"
```

### Release

```
./gradlew publishToSonatype "-Psemver.stage=[stage]" "-Psemver.scope=[scope]"
```
