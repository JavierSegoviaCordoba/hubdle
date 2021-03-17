package internal

import org.gradle.api.Project

val Project.groupId: String
    get() = "${property("libGroup")}.${property("libName")}"
