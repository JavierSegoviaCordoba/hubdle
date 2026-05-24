@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation.readme.badges

import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue

public interface HubdleDocumentationReadmeBadgesDefinition {
    @get:HasDefaultValue public val enabled: Property<Boolean>
    @get:HasDefaultValue public val mainProjectName: Property<String>
    @get:HasDefaultValue public val kotlin: Property<Boolean>
    @get:HasDefaultValue public val mavenCentral: Property<Boolean>
    @get:HasDefaultValue public val snapshots: Property<Boolean>
    @get:HasDefaultValue public val build: Property<Boolean>
    @get:HasDefaultValue public val coverage: Property<Boolean>
    @get:HasDefaultValue public val quality: Property<Boolean>
    @get:HasDefaultValue public val techDebt: Property<Boolean>
}
