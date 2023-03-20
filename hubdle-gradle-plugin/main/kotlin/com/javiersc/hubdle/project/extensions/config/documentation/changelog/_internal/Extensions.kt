package com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal

import java.io.File
import org.gradle.api.Project

internal val Project.changelogFile: File
    get() = file("${layout.projectDirectory}/CHANGELOG.md")
