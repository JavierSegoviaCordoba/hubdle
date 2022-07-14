package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public interface KotlinMultiplatformTargetOptions :
    MainAndTestKotlinSourceSetsOptions<KotlinSourceSet> {

    public val name: String

    private val targetName
        get() = name

    @HubdleDslMarker
    override fun Project.main(action: Action<KotlinSourceSet>) {
        action.execute(
            the<KotlinMultiplatformExtension>().sourceSets.maybeCreate("${targetName}Main")
        )
    }

    @HubdleDslMarker
    override fun Project.test(action: Action<KotlinSourceSet>) {
        action.execute(
            the<KotlinMultiplatformExtension>().sourceSets.maybeCreate("${targetName}Test")
        )
    }
}
