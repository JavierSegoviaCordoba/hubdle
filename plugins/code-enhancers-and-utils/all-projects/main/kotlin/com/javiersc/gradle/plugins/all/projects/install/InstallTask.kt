package com.javiersc.gradle.plugins.all.projects.install

interface InstallTask {

    fun install()

    companion object {
        internal const val taskGroup = "install"
    }
}
