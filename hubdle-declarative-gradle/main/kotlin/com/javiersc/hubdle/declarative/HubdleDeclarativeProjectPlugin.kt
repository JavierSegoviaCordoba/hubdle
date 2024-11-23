@file:Suppress("UnstableApiUsage")

package com.javiersc.hubdle.declarative

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.plugins.software.SoftwareType
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.Restricted

public abstract class HubdleDeclarativeProjectPlugin : Plugin<Project> {

    @get:SoftwareType(name = "hubdle", modelPublicType = Hubdle::class)
    public abstract val hubdle: Hubdle

    override fun apply(target: Project) {
        target.logger.lifecycle("Hubdle Declarative Project Plugin applied")
        hubdle.message.convention("Hello, World!")

        target.tasks.register("hubdle") {
            it.group = "hubdle"
            it.doLast {
                val message: String = hubdle.message.get()
                target.logger.lifecycle("Hubdle task executed, `hubdle.message = $message`")
            }
        }
    }
}

@Restricted
public interface Hubdle {

    @get:Restricted public val message: Property<String>
}
