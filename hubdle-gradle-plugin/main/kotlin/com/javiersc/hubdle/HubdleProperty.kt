package com.javiersc.hubdle

public object HubdleProperty {

    public object Nexus {
        public const val nexusSnapshotRepositoryUrl: String = "nexus.snapshotRepositoryUrl"
        public const val nexusUrl: String = "nexus.url"
        public const val ossStagingProfileId: String = "oss.stagingProfileId"
        public const val ossToken: String = "oss.token"
        public const val ossUser: String = "oss.user"
    }

    public object POM {
        public const val name: String = "pom.name"
        public const val description: String = "pom.description"
        public const val url: String = "pom.url"
        public const val licenseName: String = "pom.license.name"
        public const val licenseUrl: String = "pom.license.url"
        public const val developerId: String = "pom.developer.id"
        public const val developerName: String = "pom.developer.name"
        public const val developerEmail: String = "pom.developer.email"
        public const val scmUrl: String = "pom.scm.url"
        public const val scmConnection: String = "pom.scm.connection"
        public const val scmDeveloperConnection: String = "pom.scm.developerConnection"
    }

    public object Project {
        public const val group: String = "project.group"
        public const val mainProjectName: String = "project.main.name"
        public const val rootProjectDirName: String = "root.project.dir.name"
        public const val rootProjectName: String = "root.project.name"
        public const val version: String = "project.version"
    }

    public object Publishing {
        public const val nonSemver: String = "publishing.nonSemver"
        public const val sign: String = "publishing.sign"
    }

    public object Signing {
        public const val gnupgKey: String = "signing.gnupg.key"
        public const val gnupgKeyname: String = "signing.gnupg.keyName"
        public const val gnupgPassphrase: String = "signing.gnupg.passphrase"
        public const val keyId: String = "signing.keyId"
    }

    public object Sonar {
        public const val projectKey: String = "codeAnalysis.sonar.projectKey"
        public const val projectName: String = "codeAnalysis.sonar.projectName"
        public const val login: String = "codeAnalysis.sonar.login"
        public const val hostUrl: String = "codeAnalysis.sonar.host.url"
        public const val organization: String = "codeAnalysis.sonar.organization"
    }
}
