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
        preCommits.isEnabled = true
        action.execute(preCommits)
        hubdleState.config.install.preCommits.isEnabled = preCommits.allTests
        hubdleState.config.install.preCommits.allTests = preCommits.allTests
        hubdleState.config.install.preCommits.applyFormat = preCommits.applyFormat
        hubdleState.config.install.preCommits.assemble = preCommits.assemble
        hubdleState.config.install.preCommits.checkAnalysis = preCommits.checkAnalysis
        hubdleState.config.install.preCommits.checkApi = preCommits.checkApi
        hubdleState.config.install.preCommits.checkFormat = preCommits.checkFormat
        hubdleState.config.install.preCommits.dumpApi = preCommits.dumpApi
    }

    public open class PreCommitsExtension : EnableableOptions {
        override var isEnabled: Boolean = IS_ENABLED
        public var allTests: Boolean = ALL_TESTS
        public var applyFormat: Boolean = APPLY_FORMAT
        public var assemble: Boolean = ASSEMBLE
        public var checkAnalysis: Boolean = CHECK_ANALYSIS
        public var checkApi: Boolean = CHECK_API
        public var checkFormat: Boolean = CHECK_FORMAT
        public var dumpApi: Boolean = DUMP_API

        public companion object {
            internal const val IS_ENABLED = false
            internal const val ALL_TESTS = false
            internal const val APPLY_FORMAT = false
            internal const val ASSEMBLE = false
            internal const val CHECK_ANALYSIS = false
            internal const val CHECK_API = false
            internal const val CHECK_FORMAT = false
            internal const val DUMP_API = false
        }
    }
}
