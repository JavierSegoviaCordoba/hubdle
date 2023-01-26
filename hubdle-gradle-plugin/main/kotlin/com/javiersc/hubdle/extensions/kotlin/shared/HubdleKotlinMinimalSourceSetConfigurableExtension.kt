package com.javiersc.hubdle.extensions.kotlin.shared

import com.javiersc.hubdle.extensions._internal.MAIN
import com.javiersc.hubdle.extensions._internal.TEST
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
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName(MAIN))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName(MAIN))
        }

    override val test: NamedDomainObjectProvider<KotlinSourceSet>
        get() {
            the<KotlinProjectExtension>().sourceSets.maybeCreate(calculateName(TEST))
            return the<KotlinProjectExtension>().sourceSets.named(calculateName(TEST))
        }
}
