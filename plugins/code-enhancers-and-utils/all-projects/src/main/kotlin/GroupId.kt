@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.all.projects

import org.gradle.api.Project

val Project.groupId: String
    get() = "${property("allProjects.group")}.${property("allProjects.name")}"
