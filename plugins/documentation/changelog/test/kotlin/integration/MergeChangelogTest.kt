package com.javiersc.gradle.plugins.changelog.integration

import com.javiersc.gradle.plugins.changelog.utils.testChangelog
import com.javiersc.gradle.plugins.core.test.testSandbox
import kotlin.test.Test

class MergeChangelogTest {

    @Test
    fun `merge 1`() =
        testSandbox(sandboxPath = "merge-changelog/sandbox-merge-1", test = ::testChangelog)

    @Test
    fun `merge 2`() =
        testSandbox(sandboxPath = "merge-changelog/sandbox-merge-2", test = ::testChangelog)

    @Test
    fun `merge 3`() =
        testSandbox(sandboxPath = "merge-changelog/sandbox-merge-3", test = ::testChangelog)

    @Test
    fun `merge 4`() =
        testSandbox(sandboxPath = "merge-changelog/sandbox-merge-4", test = ::testChangelog)
}
