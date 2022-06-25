package com.javiersc.hubdle.extensions.kotlin.tools.install

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public abstract class InstallExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val preCommits: PreCommitsExtension = objects.newInstance()

    public fun Project.preCommits(action: Action<PreCommitsExtension> = Action {}) {
        action.execute(preCommits)
        hubdleState.config.apply {
            install.preCommits.allTests = preCommits.allTests
            install.preCommits.applyFormat = preCommits.applyFormat
            install.preCommits.assemble = preCommits.assemble
            install.preCommits.checkAnalysis = preCommits.checkAnalysis
            install.preCommits.checkApi = preCommits.checkApi
        }
    }

    public abstract class PreCommitsExtension {
        public var allTests: Boolean = false
        public var applyFormat: Boolean = false
        public var assemble: Boolean = false
        public var checkAnalysis: Boolean = false
        public var checkApi: Boolean = false
    }
}
