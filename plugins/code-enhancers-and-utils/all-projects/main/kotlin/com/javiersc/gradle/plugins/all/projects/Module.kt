package com.javiersc.gradle.plugins.all.projects

import org.gradle.api.Project

val Project.module: String
    get() = "${property("allProjects.group")}.${property("allProjects.name")}"
