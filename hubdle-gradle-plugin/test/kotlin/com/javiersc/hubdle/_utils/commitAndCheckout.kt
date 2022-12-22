package com.javiersc.hubdle._utils

import java.io.File
import org.eclipse.jgit.api.Git

internal fun File.commitAndCheckout(message: String, branch: String = "sandbox/hubdle") {
    val git: Git = Git.init().setDirectory(this).call()
    git.add().addFilepattern(".").call()
    git.commit().setMessage(message).call()
    git.checkout().setCreateBranch(true).setName(branch).call()
}
