package com.javiersc.hubdle.project

public object HubdleProperty {

    public object Android {
        public const val namespace: String = "android.namespace"
        public const val namespaceUseProject: String = "android.namespace.use.project"
        public const val namespaceUseKotlinFile: String = "android.namespace.use.kotlinFile"

        public object BuildFeatures {
            public const val aidl: String = "android.defaults.buildfeatures.aidl"
            public const val buildConfig: String = "android.defaults.buildfeatures.buildconfig"
            public const val compose: String = "android.defaults.buildfeatures.compose"
            public const val renderScript: String = "android.defaults.buildfeatures.renderscript"
            public const val resValues: String = "android.defaults.buildfeatures.resvalues"
            public const val shaders: String = "android.defaults.buildfeatures.shaders"
            public const val viewBinding: String = "android.defaults.buildfeatures.viewbinding"
        }
    }

    public object Analysis {
        public object Qodana {
            public const val projectKey: String = "analysis.qodana.projectKey"
        }
        public object Sonar {
            public const val hostUrl: String = "analysis.sonar.host.url"
            public const val login: String = "analysis.sonar.login"
            public const val organization: String = "analysis.sonar.organization"
            public const val projectKey: String = "analysis.sonar.projectKey"
            public const val projectName: String = "analysis.sonar.projectName"
        }
    }

    public object JetBrains {
        public const val marketplaceCertificateChain: String =
            "jetbrains.marketplace.certificateChain"
        public const val marketplaceKey: String = "jetbrains.marketplace.key"
        public const val marketplaceKeyPassphrase: String = "jetbrains.marketplace.keyPassphrase"
    }

    public object IntelliJ {
        public const val downloadSources: String = "intellij.downloadSources"
        public const val publishToken: String = "intellij.publishToken"
        public const val type: String = "intellij.type"
        public const val sinceBuild: String = "intellij.sinceBuild"
        public const val untilBuild: String = "intellij.untilBuild"
        public const val updateSinceUntilBuild: String = "intellij.updateSinceUntilBuild"
        public const val version: String = "intellij.version"
    }

    public object Nexus {
        public const val nexusSnapshotRepositoryUrl: String = "nexus.snapshotRepositoryUrl"
        public const val nexusStagingProfileId: String = "nexus.stagingProfileId"
        public const val nexusToken: String = "nexus.token"
        public const val nexusUrl: String = "nexus.url"
        public const val nexusUser: String = "nexus.user"
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
        public const val mainProjectName: String = "main.project.name"
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
        public const val gnupgPassphrase: String = "signing.gnupg.passphrase"
        public const val keyId: String = "signing.keyId"
    }
}
