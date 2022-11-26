package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun configureMultiplatformJs(project: Project) {
    val jsState = project.hubdleState.kotlin.multiplatform.js
    if (jsState.isEnabled) {
        project.configure<KotlinMultiplatformExtension> {
            js {
                if (jsState.browser.isEnabled) {
                    browser { jsState.browser.action?.execute(this) }
                }
                if (jsState.nodejs.isEnabled) {
                    nodejs { jsState.nodejs.action?.execute(this) }
                }
            }
        }
    }
}
