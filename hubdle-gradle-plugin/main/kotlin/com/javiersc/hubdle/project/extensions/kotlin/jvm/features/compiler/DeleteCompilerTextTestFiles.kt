package com.javiersc.hubdle.project.extensions.kotlin.jvm.features.compiler

import com.javiersc.kotlin.stdlib.notContain
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
public open class DeleteCompilerTextTestFiles
@Inject
constructor(layout: ProjectLayout, objects: ObjectFactory) : DefaultTask() {

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    public val inputDirectory: DirectoryProperty =
        objects.directoryProperty().convention(layout.projectDirectory.dir("test-data"))

    @get:OutputDirectory
    public val outputDirectory: DirectoryProperty =
        objects.directoryProperty().convention(layout.projectDirectory.dir("test-data"))

    @TaskAction
    public fun deleteFiles() {
        val textFiles: List<File> =
            inputDirectory
                .get()
                .asFile
                .walkTopDown()
                .filter { it.isFile }
                .filter { it.extension == "txt" }
                .filter { it.path.notContain("TODO", ignoreCase = true) }
                .filter { it.path.notContain("DISABLED", ignoreCase = true) }
                .toList()

        for (textFile in textFiles) textFile.delete()
    }

    public companion object {
        public const val NAME: String = "deleteCompilerTextTestFiles"
    }
}
