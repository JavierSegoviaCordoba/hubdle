# Publish

## Prerequisites

1. Generate key: `gpg --full-generate-key`
2. Check key id: `gpg --list-signatures`
3. Upload to server: `gpg --keyserver hkps://keys.openpgp.org --send-keys [keyId]`
4. Show the private key: `gpg --armor --export-secret-keys [keyId]`

> You can use GUI utilities:
> - Kleopatra for Windows
> - GPG Suite (GPGTools) for macOS

## Versioning

TODO

## Configure CI/CD (GitHub)

### GitHub secrets

- KeyId as `GPG_KEY_NAME`
- Passphrase as `GPG_KEYPHRASE`
- Private key as `GPG_PRIVATE_KEY`
    - Get the key using the command from point 4 in prerequisites.
- Sonatype - Nexus user as `OSS_USER`
- Sonatype - Nexus token as `OSS_TOKEN`
- Sonatype - Nexus Profile ID as `OSS_STAGING_PROFILE_ID`
    - Visit this [link](https://oss.sonatype.org/#stagingProfiles), select your profile and copy
      `SOME_NUMBER` from the url `https://oss.sonatype.org/#stagingProfiles;SOME_NUMBERS`
