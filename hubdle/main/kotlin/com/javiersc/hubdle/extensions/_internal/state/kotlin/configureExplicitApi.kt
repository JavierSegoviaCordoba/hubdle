package com.javiersc.hubdle.extensions._internal.state.kotlin

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.kotlinExtension
import org.gradle.api.Project

internal fun Project.configureExplicitApi() {
    kotlinExtension.explicitApi = hubdleState.kotlin.explicitApiMode
}
