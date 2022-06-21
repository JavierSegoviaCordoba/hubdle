package com.javiersc.hubdle.extensions._internal.state

import org.gradle.api.Project

internal fun Project.configureState() {
    hubdleState.configure(this)
}
