package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

open class PreCommitOptions @Inject constructor(objects: ObjectFactory) {

    val allTests: Property<Boolean> = objects.property<Boolean>().convention(true)
    val apiCheck: Property<Boolean> = objects.property<Boolean>().convention(true)
    val assemble: Property<Boolean> = objects.property<Boolean>().convention(true)
    val spotlessCheck: Property<Boolean> = objects.property<Boolean>().convention(true)
}
