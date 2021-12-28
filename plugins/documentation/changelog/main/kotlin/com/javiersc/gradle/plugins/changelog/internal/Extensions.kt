package com.javiersc.gradle.plugins.changelog.internal

import java.io.File
import org.gradle.api.Project

internal val Project.changelogFile: File
    get() = file("$projectDir/CHANGELOG.md")
