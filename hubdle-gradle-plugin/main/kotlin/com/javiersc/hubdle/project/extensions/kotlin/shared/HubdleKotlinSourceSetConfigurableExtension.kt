package com.javiersc.hubdle.project.extensions.kotlin.shared

import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.TEST
import com.javiersc.hubdle.project.extensions._internal.TEST_FIXTURES
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.project.extensions._internal.TEST_INTEGRATION
import com.javiersc.hubdle.project.extensions.apis.HubdleSourceSetConfigurableExtension
import com.javiersc.hubdle.project.extensions.kotlin.shared.asString as originalAsString
import com.javiersc.hubdle.project.extensions.kotlin.shared.moduleAsString as originalModuleAsString
import com.javiersc.hubdle.project.extensions.kotlin.shared.versionAsString as originalVersionAsString
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public abstract class HubdleKotlinSourceSetConfigurableExtension(
    project: Project,
) : HubdleSourceSetConfigurableExtension<KotlinSourceSet>(project) {

    override val main: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName(MAIN))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName(MAIN))
        }

    override val test: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName(TEST))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName(TEST))
        }

    override val testFunctional: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName(TEST_FUNCTIONAL))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName(TEST_FUNCTIONAL))
        }

    override val testIntegration: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName(TEST_INTEGRATION))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName(TEST_INTEGRATION))
        }

    override val testFixtures: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName(TEST_FIXTURES))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName(TEST_FIXTURES))
        }

    public fun Provider<MinimalExternalModuleDependency>.asString(): String = originalAsString()

    public fun Provider<MinimalExternalModuleDependency>.moduleAsString(): String =
        originalModuleAsString()

    public fun Provider<MinimalExternalModuleDependency>.versionAsString(): String? =
        originalVersionAsString()
}
