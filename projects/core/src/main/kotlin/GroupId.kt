package com.javiersc.plugins.core

import org.gradle.api.Project

val Project.groupId: String
    get() = "${property("allProjects.group")}.${property("allProjects.name")}"
