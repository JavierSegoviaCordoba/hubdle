package com.javiersc.hubdle.extensions.kotlin.shared

import com.javiersc.hubdle.extensions.apis.HubdleSourceSetConfigurableExtension
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public abstract class HubdleKotlinSourceSetConfigurableExtension(
    project: Project,
) : HubdleSourceSetConfigurableExtension<KotlinSourceSet>(project) {

    override val main: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName("main"))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName("main"))
        }

    override val test: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName("test"))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName("test"))
        }

    override val testFunctional: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName("testFunctional"))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName("testFunctional"))
        }

    override val testIntegration: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName("testIntegration"))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName("testIntegration"))
        }

    override val testFixtures: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName("testFixtures"))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName("testFixtures"))
        }
}
