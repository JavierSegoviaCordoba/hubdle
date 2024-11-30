package hubdle.declarative.platform

@Suppress("ConstPropertyName")
public object PlatformProperties {

    public const val ProjectGroup: String = "hubdle.project.group"
    public const val ProjectName: String = "hubdle.project.name"

    public object Pom {
        public const val description: String = "hubdle.pom.description"
        public const val name: String = "hubdle.pom.name"
        public const val url: String = "hubdle.pom.url"

        public object Developer {
            public const val email: String = "hubdle.pom.developer.email"
            public const val id: String = "hubdle.pom.developer.id"
            public const val name: String = "hubdle.pom.developer.name"
        }

        public object License {
            public const val name: String = "hubdle.pom.license.name"
            public const val url: String = "hubdle.pom.license.url"
        }

        public object Scm {
            public const val connection: String = "hubdle.pom.scm.connection"
            public const val DeveloperConnection: String = "hubdle.pom.scm.developerConnection"
            public const val url: String = "hubdle.pom.scm.url"
        }
    }
}
