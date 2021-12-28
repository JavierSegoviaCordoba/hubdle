package com.javiersc.gradle.plugins.docs

import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

open class NavigationOptions @Inject constructor(objects: ObjectFactory) {

    fun reports(action: Action<in Reports>) = action.execute(reports.get())

    val reports: Property<Reports> =
        objects.property<Reports>().value(objects.newInstance(Reports::class.java))

    open class Reports @Inject constructor(objects: ObjectFactory) {

        val allTests: Property<Boolean> = objects.property<Boolean>().convention(true)
        val codeAnalysis: Property<Boolean> = objects.property<Boolean>().convention(true)
        val codeCoverage: Property<Boolean> = objects.property<Boolean>().convention(true)
        val codeQuality: Property<Boolean> = objects.property<Boolean>().convention(true)
    }
}
