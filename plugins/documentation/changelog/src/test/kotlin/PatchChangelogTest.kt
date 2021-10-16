package com.javiersc.gradle.plugins.changelog

import com.javiersc.gradle.plugins.changelog.utils.testChangelog
import com.javiersc.gradle.plugins.core.test.testSandbox
import org.junit.Test

class PatchChangelogTest {

    @Test
    fun `patch 1`() =
        testSandbox(sandboxPath = "patch-changelog/sandbox-patch-1", test = ::testChangelog)
}
