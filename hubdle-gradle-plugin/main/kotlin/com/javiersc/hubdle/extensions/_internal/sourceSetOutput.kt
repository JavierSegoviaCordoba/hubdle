package com.javiersc.hubdle.extensions._internal

import com.javiersc.hubdle.extensions.apis.HubdleSourceSetConfigurableExtension
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.SourceSetOutput
import org.gradle.kotlin.dsl.the

/**
 * Sample:
 * ```kotlin
 * sourceSetOutput("testFixtures")
 * ```
 */
internal fun HubdleSourceSetConfigurableExtension<*>.sourceSetOutput(
    sourceSetName: String
): Provider<SourceSetOutput> =
    project.the<SourceSetContainer>().named(sourceSetName).map(SourceSet::getOutput)
