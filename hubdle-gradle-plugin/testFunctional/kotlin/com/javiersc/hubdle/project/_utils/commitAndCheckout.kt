package com.javiersc.hubdle.project._utils

import java.io.File
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Config
import org.eclipse.jgit.lib.GpgConfig

internal fun File.commitAndCheckout(message: String, branch: String = "sandbox/hubdle") {
    val git: Git = Git.init().setDirectory(this).call()
    git.add().addFilepattern(".").call()
    git.commit().setSign(false).setGpgConfig(GpgConfig(Config())).setMessage(message).call()
    git.checkout().setCreateBranch(true).setName(branch).call()
}
