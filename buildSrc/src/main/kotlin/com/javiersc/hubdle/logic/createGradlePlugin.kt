package com.javiersc.hubdle.logic

import com.javiersc.kotlin.stdlib.capitalize
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.plugin.devel.PluginDeclaration

fun NamedDomainObjectContainer<PluginDeclaration>.createGradlePlugin(
    pluginDeclarationName: String,
    pluginName: String,
) {
    val baseId = "com.javiersc.hubdle.declarative"
    val basePackage: String = baseId.substringAfter("com.javiersc.")
    val realId = "$baseId.${pluginDeclarationName.replace("-", ".")}"
    val realPackage = "$basePackage.${pluginDeclarationName.replace("-", ".")}"
    create("$pluginDeclarationName-project") {
        id = "$realId.project"
        displayName = realPackage.replace(".", " ").capitalize()
        description = "$displayName Gradle Project Plugin"
        implementationClass = "$realPackage.${pluginName}ProjectPlugin"
        tags.set(displayName.split(" "))
    }
    create("$pluginDeclarationName-settings") {
        id = "$realId.settings"
        displayName = realPackage.replace(".", " ").capitalize()
        description = "$displayName Gradle Settings Plugin"
        implementationClass = "$realPackage.${pluginName}SettingsPlugin"
        tags.set(displayName.split(" "))
    }
}
