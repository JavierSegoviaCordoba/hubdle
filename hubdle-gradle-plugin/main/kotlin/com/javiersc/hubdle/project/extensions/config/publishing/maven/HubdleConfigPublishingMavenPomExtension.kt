package com.javiersc.hubdle.project.extensions.config.publishing.maven

import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property

@HubdleDslMarker
public open class HubdleConfigPublishingMavenPomExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdlePublishingMaven)

    public val description: Property<String> = property { getStringProperty(POM.description).get() }

    @HubdleDslMarker
    public fun description(value: String) {
        description.set(value)
    }

    public val developerEmail: Property<String> = property {
        getStringProperty(POM.developerEmail).get()
    }

    @HubdleDslMarker
    public fun developerEmail(value: String) {
        developerEmail.set(value)
    }

    public val developerId: Property<String> = property { getStringProperty(POM.developerId).get() }

    @HubdleDslMarker
    public fun developerId(value: String) {
        developerId.set(value)
    }

    public val developerName: Property<String> = property {
        getStringProperty(POM.developerName).get()
    }

    @HubdleDslMarker
    public fun developerName(value: String) {
        developerName.set(value)
    }

    public val licenseName: Property<String> = property { getStringProperty(POM.licenseName).get() }

    @HubdleDslMarker
    public fun licenseName(value: String) {
        licenseName.set(value)
    }

    public val licenseUrl: Property<String> = property { getStringProperty(POM.licenseUrl).get() }

    @HubdleDslMarker
    public fun licenseUrl(value: String) {
        licenseUrl.set(value)
    }

    public val name: Property<String> = property { getStringProperty(POM.name).get() }

    @HubdleDslMarker
    public fun name(value: String) {
        name.set(value)
    }

    public val scmConnection: Property<String> = property {
        getStringProperty(POM.scmConnection).get()
    }

    @HubdleDslMarker
    public fun scmConnection(value: String) {
        scmConnection.set(value)
    }

    public val scmDeveloperConnection: Property<String> = property {
        getStringProperty(POM.scmDeveloperConnection).get()
    }

    @HubdleDslMarker
    public fun scmDeveloperConnection(value: String) {
        scmDeveloperConnection.set(value)
    }

    public val scmUrl: Property<String> = property { getStringProperty(POM.scmUrl).get() }

    @HubdleDslMarker
    public fun scmUrl(value: String) {
        scmUrl.set(value)
    }

    public val url: Property<String> = property { getStringProperty(POM.url).get() }

    @HubdleDslMarker
    public fun url(value: String) {
        url.set(value)
    }

    public object POM {
        public const val description: String = "pom.description"
        public const val developerEmail: String = "pom.developer.email"
        public const val developerId: String = "pom.developer.id"
        public const val developerName: String = "pom.developer.name"
        public const val licenseName: String = "pom.license.name"
        public const val licenseUrl: String = "pom.license.url"
        public const val name: String = "pom.name"
        public const val scmConnection: String = "pom.scm.connection"
        public const val scmDeveloperConnection: String = "pom.scm.developerConnection"
        public const val scmUrl: String = "pom.scm.url"
        public const val url: String = "pom.url"
    }
}

internal val Project.hubdlePublishingMavenPom: HubdleConfigPublishingMavenPomExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdlePublishingMavenPom:
    HubdleConfigPublishingMavenPomExtension
    get() = getHubdleExtension()
