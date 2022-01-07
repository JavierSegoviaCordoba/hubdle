package com.javiersc.gradle.plugins.all.projects.install

import com.javiersc.gradle.plugins.all.projects.install.pre.commit.PreCommitOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

open class InstallOptions @Inject constructor(objects: ObjectFactory) {
    fun preCommit(action: Action<in PreCommitOptions>) = action.execute(preCommit.get())

    val preCommit: Property<PreCommitOptions> =
        objects
            .property<PreCommitOptions>()
            .value(objects.newInstance(PreCommitOptions::class.java))
}
