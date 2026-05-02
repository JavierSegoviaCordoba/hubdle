@file:Suppress("ConstPropertyName")

package hubdle.platform

public object HubdleProperties {

    public object Logging {
        public const val Enabled: String = "hubdle.logging.enabled"
    }

    public object Pom {
        public const val Description: String = "hubdle.pom.description"
        public const val Name: String = "hubdle.pom.name"
        public const val Url: String = "hubdle.pom.url"

        public object Developer {
            public const val Email: String = "hubdle.pom.developer.email"
            public const val Id: String = "hubdle.pom.developer.id"
            public const val Name: String = "hubdle.pom.developer.name"
        }

        public object License {
            public const val Name: String = "hubdle.pom.license.name"
            public const val Url: String = "hubdle.pom.license.url"
        }

        public object Scm {
            public const val Connection: String = "hubdle.pom.scm.connection"
            public const val DeveloperConnection: String = "hubdle.pom.scm.developerConnection"
            public const val Url: String = "hubdle.pom.scm.url"
        }
    }

    public object Project {
        public const val Group: String = "hubdle.project.group"
        public const val Name: String = "hubdle.project.name"
    }

    public object Sonar {
        public const val ProjectKey: String = "hubdle.sonar.projectKey"
    }
}
