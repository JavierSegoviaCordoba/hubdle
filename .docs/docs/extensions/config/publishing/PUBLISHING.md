# Publishing

Configure the project to be automatically published.

## Configuration

### Basic config

```kotlin
hubdle {
    config {
        publishing()
    }
}
```

Local properties, environment variables or Gradle properties

```properties
# POM                                                ###
pom.name=Hubdle
pom.description=Hubdle description
pom.url=https://github.com/JavierSegoviaCordoba/hubdle
pom.license.name=The Apache License, Version 2.0
pom.license.url=https://www.apache.org/licenses/LICENSE-2.0.txt
pom.developer.id=JavierSegoviaCordoba
pom.developer.name=Javier Segovia Cordoba
pom.developer.email=javier@segoviacordoba.com
pom.scm.url=https://github.com/JavierSegoviaCordoba/hubdle
pom.scm.connection=scm:git:git@github.com:JavierSegoviaCordoba/hubdle.git
pom.scm.developerConnection=scm:git:git@github.com:JavierSegoviaCordoba/hubdle.git
# Signing
signing.gnupg.key=...
signing.gnupg.keyName=...
signing.gnupg.passphrase=...
signing.keyId=...
# Gradle Publish plugin
gradle.publish.key=...
gradle.publish.secret=...
# Others
publishing.nonSemver=false
publishing.sign=true
```

Any property can be provided in screaming case too (except Gradle Publish plugin properties):

```properties
# POM                                                ###
POM_NAME=Hubdle
POM_DESCRIPTION=Hubdle description
POM_URL=https://github.com/JavierSegoviaCordoba/hubdle
POM_LICENSE_NAME=The Apache License, Version 2.0
POM_LICENSE_URL=https://www.apache.org/licenses/LICENSE-2.0.txt
POM_DEVELOPER_ID=JavierSegoviaCordoba
POM_DEVELOPER_NAME=Javier Segovia Cordoba
POM_DEVELOPER_EMAIL=javier@segoviacordoba.com
POM_SCM_URL=https://github.com/JavierSegoviaCordoba/hubdle
POM_SCM_CONNECTION=scm:git:git@github.com:JavierSegoviaCordoba/hubdle.git
POM_SCM_DEVELOPER_CONNECTION=scm:git:git@github.com:JavierSegoviaCordoba/hubdle.git
# Signing
SIGNING_GNUPG_KEY=...
SIGNING_GNUPG_KEY_NAME=...
SIGNING_GNUPG_PASSPHRASE=...
SIGNING_KEY_ID=...
# Others
PUBLISHING_NON_SEMVER=false
PUBLISHING_SIGN=true
```
