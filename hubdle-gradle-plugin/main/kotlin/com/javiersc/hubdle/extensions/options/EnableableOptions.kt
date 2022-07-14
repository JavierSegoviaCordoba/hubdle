package com.javiersc.hubdle.extensions.options

import org.gradle.api.Project

public interface EnableableOptions {

    public var Project.isEnabled: Boolean
}

public interface EnableableAllOptions {

    public var Project.allEnabled: Boolean

    public fun Project.enableAll(enable: Boolean = true) {
        allEnabled = enable
    }
}
