package com.javiersc.hubdle.properties

import org.gradle.api.Project

public val Project.isSnapshot: Boolean
    get() = version.toString().endsWith("-SNAPSHOT", ignoreCase = true)

public val Project.isNotSnapshot: Boolean
    get() = !isSnapshot
