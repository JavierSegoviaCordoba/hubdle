package com.javiersc.hubdle.extensions.options

import org.gradle.api.Project

public interface EnableableOptions {

    public var Project.isEnabled: Boolean
}
