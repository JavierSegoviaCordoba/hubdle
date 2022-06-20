package com.javiersc.hubdle.extensions.kotlin.tools.explicit.api._internal

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun Project.configureExplicitApi() {
    the<KotlinProjectExtension>().explicitApi = hubdleState.kotlin.tools.explicitApiMode
}
