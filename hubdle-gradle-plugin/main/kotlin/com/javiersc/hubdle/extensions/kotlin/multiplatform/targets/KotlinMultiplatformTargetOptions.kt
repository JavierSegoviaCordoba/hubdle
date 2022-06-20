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

    @HubdleDslMarker
    override fun Project.main(action: Action<KotlinSourceSet>) {
        the<KotlinMultiplatformExtension>().sourceSets.named("${name}Main", action::execute)
    }

    @HubdleDslMarker
    override fun Project.test(action: Action<KotlinSourceSet>) {
        the<KotlinMultiplatformExtension>().sourceSets.named("${name}Test", action::execute)
    }
}
