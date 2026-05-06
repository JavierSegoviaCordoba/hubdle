@file:Suppress("UnstableApiUsage")

package hubdle.declarative.kotlin

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import org.gradle.declarative.dsl.model.annotations.Adding
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue
import org.gradle.features.binding.Definition

public interface HubdleKotlinDefinition : HubdleDefinition, Definition<HubdleKotlinBuildModel> {

    override val featureName: String
        get() = "kotlin"

    @get:Nested public val analysis: HubdleKotlinAnalysisDefinition

    @get:Nested public val compilerOptions: HubdleKotlinCompilerOptionsDefinition

    @get:Nested public val coverage: HubdleKotlinCoverageDefinition

    @get:Nested public val documentation: HubdleKotlinDocumentationDefinition

    @get:Nested public val explicitApi: HubdleKotlinExplicitApiDefinition

    @get:Nested public val features: HubdleKotlinFeaturesDefinition

    @get:Nested public val format: HubdleKotlinFormatDefinition

    @get:Nested public val languageSettings: HubdleKotlinLanguageSettingsDefinition

    @get:Nested public val testing: HubdleKotlinTestingDefinition
}

public interface HubdleKotlinAnalysisDefinition : HubdleKotlinEnableableDefinition

public interface HubdleKotlinAtomicfuFeatureDefinition : HubdleKotlinEnableableDefinition {

    @get:HasDefaultValue public val jsIrTransformationEnabled: Property<Boolean>

    @get:HasDefaultValue public val jvmIrTransformationEnabled: Property<Boolean>

    @get:HasDefaultValue public val nativeIrTransformationEnabled: Property<Boolean>
}

public interface HubdleKotlinBuildKonfigFeatureDefinition : HubdleKotlinEnableableDefinition

public interface HubdleKotlinCompilerOptionsDefinition {

    public val apiVersion: Property<String>

    public val languageVersion: Property<String>
}

public interface HubdleKotlinComposeFeatureDefinition : HubdleKotlinEnableableDefinition {

    public val compiler: Property<String>

    public val compilerVersion: Property<String>
}

public interface HubdleKotlinCoroutinesFeatureDefinition : HubdleKotlinEnableableDefinition

public interface HubdleKotlinCoverageDefinition : HubdleKotlinEnableableDefinition {

    public val jacoco: Property<String>
}

public interface HubdleKotlinDocumentationDefinition : HubdleKotlinEnableableDefinition

public interface HubdleKotlinEnableableDefinition {

    @get:HasDefaultValue public val enabled: Property<Boolean>
}

public interface HubdleKotlinExplicitApiDefinition : HubdleKotlinEnableableDefinition {

    public val mode: Property<String>
}

public interface HubdleKotlinExtendedStdlibFeatureDefinition : HubdleKotlinEnableableDefinition

public interface HubdleKotlinFeaturesDefinition : HubdleKotlinEnableableDefinition {

    @get:Nested public val atomicfu: HubdleKotlinAtomicfuFeatureDefinition

    @Adding
    public fun atomicfu(enabled: Boolean = true) {
        atomicfu.enabled.set(enabled)
    }

    @get:Nested public val buildKonfig: HubdleKotlinBuildKonfigFeatureDefinition

    @Adding
    public fun buildKonfig(enabled: Boolean = true) {
        buildKonfig.enabled.set(enabled)
    }

    @get:Nested public val compose: HubdleKotlinComposeFeatureDefinition

    @Adding
    public fun compose(enabled: Boolean = true) {
        compose.enabled.set(enabled)
    }

    @get:Nested public val coroutines: HubdleKotlinCoroutinesFeatureDefinition

    @Adding
    public fun coroutines(enabled: Boolean = true) {
        coroutines.enabled.set(enabled)
    }

    @get:Nested public val extendedStdlib: HubdleKotlinExtendedStdlibFeatureDefinition

    @Adding
    public fun extendedStdlib(enabled: Boolean = true) {
        extendedStdlib.enabled.set(enabled)
    }

    @get:Nested public val kopy: HubdleKotlinKopyFeatureDefinition

    @Adding
    public fun kopy(enabled: Boolean = true) {
        kopy.enabled.set(enabled)
    }

    @get:Nested public val kotest: HubdleKotlinKotestFeatureDefinition

    @Adding
    public fun kotest(enabled: Boolean = true) {
        kotest.enabled.set(enabled)
    }

    @get:Nested public val molecule: HubdleKotlinMoleculeFeatureDefinition

    @Adding
    public fun molecule(enabled: Boolean = true) {
        molecule.enabled.set(enabled)
    }

    @get:Nested public val powerAssert: HubdleKotlinPowerAssertFeatureDefinition

    @Adding
    public fun powerAssert(enabled: Boolean = true) {
        powerAssert.enabled.set(enabled)
    }

    @get:Nested public val serialization: HubdleKotlinSerializationFeatureDefinition

    @Adding
    public fun serialization(enabled: Boolean = true) {
        serialization.enabled.set(enabled)
    }

    @get:Nested public val sqlDelight: HubdleKotlinSqlDelightFeatureDefinition

    @Adding
    public fun sqlDelight(enabled: Boolean = true) {
        sqlDelight.enabled.set(enabled)
    }
}

public interface HubdleKotlinFormatDefinition : HubdleKotlinEnableableDefinition {

    public val excludes: ListProperty<String>

    public val includes: ListProperty<String>

    public val ktfmtVersion: Property<String>

    @Adding
    public fun exclude(value: String) {
        excludes.add(value)
    }

    @Adding
    public fun include(value: String) {
        includes.add(value)
    }
}

public interface HubdleKotlinKopyFeatureDefinition : HubdleKotlinEnableableDefinition {

    public val functions: ListProperty<String>

    public val visibility: Property<String>

    @Adding
    public fun function(value: String) {
        functions.add(value)
    }
}

public interface HubdleKotlinKotestFeatureDefinition : HubdleKotlinEnableableDefinition

public interface HubdleKotlinLanguageSettingsDefinition {

    public val enabledLanguageFeatures: ListProperty<String>

    public val optIns: ListProperty<String>

    @Adding
    public fun enableLanguageFeature(name: String) {
        enabledLanguageFeatures.add(name)
    }

    @Adding
    public fun optIn(annotationName: String) {
        optIns.add(annotationName)
    }
}

public interface HubdleKotlinMoleculeFeatureDefinition : HubdleKotlinEnableableDefinition

public interface HubdleKotlinPowerAssertFeatureDefinition : HubdleKotlinEnableableDefinition {

    public val functions: ListProperty<String>

    @Adding
    public fun function(value: String) {
        functions.add(value)
    }
}

public interface HubdleKotlinSerializationFeatureDefinition : HubdleKotlinEnableableDefinition {

    @get:HasDefaultValue public val csv: Property<Boolean>

    @get:HasDefaultValue public val flf: Property<Boolean>

    @get:HasDefaultValue public val json: Property<Boolean>
}

public interface HubdleKotlinSqlDelightFeatureDefinition : HubdleKotlinEnableableDefinition

public interface HubdleKotlinTestingDefinition : HubdleKotlinEnableableDefinition {

    public val maxParallelForks: Property<Int>

    public val options: Property<String>

    @get:HasDefaultValue public val showStandardStreams: Property<Boolean>
}
