package com.javiersc.hubdle.extensions

import com.javiersc.hubdle.extensions.config.ConfigExtension
import com.javiersc.hubdle.extensions.kotlin.KotlinExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

public abstract class HubdleExtension @Inject constructor(objects: ObjectFactory) {

    public val config: ConfigExtension = objects.newInstance()

    public fun config(action: Action<in ConfigExtension>) {
        action.execute(config)
    }

    public val kotlin: KotlinExtension = objects.newInstance()

    public fun kotlin(action: Action<in KotlinExtension>) {
        action.execute(kotlin)
    }
}
