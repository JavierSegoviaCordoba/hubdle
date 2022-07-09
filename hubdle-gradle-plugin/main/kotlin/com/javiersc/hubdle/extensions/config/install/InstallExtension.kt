package com.javiersc.hubdle.extensions.config.install

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class InstallExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val preCommits: PreCommitsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.preCommits(action: Action<PreCommitsExtension> = Action {}) {
        preCommits.run { isEnabled = true }
        action.execute(preCommits)
    }

    public open class PreCommitsExtension : EnableableOptions {
        override var Project.isEnabled: Boolean
            get() = hubdleState.config.install.preCommits.isEnabled
            set(value) = hubdleState.config.install.preCommits.run { isEnabled = value }

        public var Project.allTests: Boolean
            get() = hubdleState.config.install.preCommits.allTests
            set(value) = hubdleState.config.install.preCommits.run { allTests = value }

        public var Project.applyFormat: Boolean
            get() = hubdleState.config.install.preCommits.applyFormat
            set(value) = hubdleState.config.install.preCommits.run { applyFormat = value }

        public var Project.assemble: Boolean
            get() = hubdleState.config.install.preCommits.assemble
            set(value) = hubdleState.config.install.preCommits.run { assemble = value }

        public var Project.checkAnalysis: Boolean
            get() = hubdleState.config.install.preCommits.checkAnalysis
            set(value) = hubdleState.config.install.preCommits.run { checkAnalysis = value }

        public var Project.checkApi: Boolean
            get() = hubdleState.config.install.preCommits.checkApi
            set(value) = hubdleState.config.install.preCommits.run { checkApi = value }

        public var Project.checkFormat: Boolean
            get() = hubdleState.config.install.preCommits.checkFormat
            set(value) = hubdleState.config.install.preCommits.run { checkFormat = value }

        public var Project.dumpApi: Boolean
            get() = hubdleState.config.install.preCommits.dumpApi
            set(value) = hubdleState.config.install.preCommits.run { dumpApi = value }
    }
}
