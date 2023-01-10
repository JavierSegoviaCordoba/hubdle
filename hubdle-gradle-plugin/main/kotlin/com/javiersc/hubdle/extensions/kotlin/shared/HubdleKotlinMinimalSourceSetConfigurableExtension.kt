package com.javiersc.hubdle.extensions.kotlin.shared

import com.javiersc.hubdle.extensions.apis.HubdleMinimalSourceSetConfigurableExtension
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public abstract class HubdleKotlinMinimalSourceSetConfigurableExtension(
    project: Project,
) : HubdleMinimalSourceSetConfigurableExtension<KotlinSourceSet>(project) {

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
}
