@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.install

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import org.gradle.declarative.dsl.model.annotations.Adding
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue
import org.gradle.features.binding.Definition

public interface HubdleInstallDefinition : HubdleDefinition, Definition<HubdleInstallBuildModel> {

    override val featureName: String
        get() = "install"

    @get:Nested public val preCommits: HubdleInstallPreCommitsDefinition

    @Adding
    public fun preCommits(enabled: Boolean = true) {
        preCommits.enabled.set(enabled)
    }
}

public interface HubdleInstallPreCommitsDefinition {
    @get:HasDefaultValue public val enabled: Property<Boolean>
    @get:HasDefaultValue public val applyFormat: Property<Boolean>
    @get:HasDefaultValue public val assemble: Property<Boolean>
    @get:HasDefaultValue public val checkAnalysis: Property<Boolean>
    @get:HasDefaultValue public val checkApi: Property<Boolean>
    @get:HasDefaultValue public val checkFormat: Property<Boolean>
    @get:HasDefaultValue public val dumpApi: Property<Boolean>
    @get:HasDefaultValue public val tests: Property<Boolean>

    @Adding
    public fun applyFormat(enabled: Boolean = true) {
        applyFormat.set(enabled)
    }

    @Adding
    public fun assemble(enabled: Boolean = true) {
        assemble.set(enabled)
    }

    @Adding
    public fun checkAnalysis(enabled: Boolean = true) {
        checkAnalysis.set(enabled)
    }

    @Adding
    public fun checkApi(enabled: Boolean = true) {
        checkApi.set(enabled)
    }

    @Adding
    public fun checkFormat(enabled: Boolean = true) {
        checkFormat.set(enabled)
    }

    @Adding
    public fun dumpApi(enabled: Boolean = true) {
        dumpApi.set(enabled)
    }

    @Adding
    public fun tests(enabled: Boolean = true) {
        tests.set(enabled)
    }
}
