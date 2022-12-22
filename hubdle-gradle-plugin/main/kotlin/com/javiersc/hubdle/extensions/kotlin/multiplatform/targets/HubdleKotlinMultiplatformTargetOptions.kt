package com.javiersc.hubdle.extensions.kotlin.multiplatform.targets

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.hubdleState
import com.javiersc.hubdle.extensions._internal.property
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public interface HubdleKotlinMultiplatformTargetOptions {

    public val project: Project

    public val targetName: String

    public val isEnabled: Property<Boolean>

    private val configurableName
        get() = "KotlinMultiplatformTarget: $targetName"

    private val isTargetEnabled: Property<Boolean>
        get() =
            project.property { hubdleKotlinMultiplatform.isFullEnabled.get() && isEnabled.get() }

    @HubdleDslMarker
    public fun main(action: Action<KotlinSourceSet>) {
        project.hubdleState.userConfigurable(configurableName, isTargetEnabled) {
            project.configure<KotlinMultiplatformExtension> {
                val sourceSet = sourceSets.maybeCreate("${targetName}Main")
                action.execute(sourceSet)
            }
        }
    }

    @HubdleDslMarker
    public fun test(action: Action<KotlinSourceSet>) {
        project.hubdleState.userConfigurable(configurableName, isTargetEnabled) {
            project.configure<KotlinMultiplatformExtension> {
                val sourceSet = sourceSets.maybeCreate("${targetName}Test")
                action.execute(sourceSet)
            }
        }
    }
}
