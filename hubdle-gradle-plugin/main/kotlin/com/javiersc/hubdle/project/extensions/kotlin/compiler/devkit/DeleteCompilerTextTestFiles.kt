package com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit

import com.javiersc.kotlin.stdlib.notContain
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

/**
 * Deletes the dumps of the compiler tests, so the next test run writes them again.
 *
 * It looks in the test data directories of every DevKit test source set, for example
 * `common/test/data`, and deletes the text dumps (`.txt`, for example `foo.fir.txt` or the
 * classified `foo.2420.fir.txt`) and the diff dumps (`.patch`). It keeps the files with `TODO` or
 * `DISABLED` in their path.
 */
@DisableCachingByDefault(because = "It deletes files")
public open class DeleteCompilerTextTestFiles @Inject constructor(objects: ObjectFactory) :
    DefaultTask() {

    @get:Internal
    public val testDataDirectories: ConfigurableFileCollection = objects.fileCollection()

    init {
        group = "verification"
        description = "Deletes the dumps of the compiler tests"
    }

    @TaskAction
    public fun deleteFiles() {
        val dumps: List<File> =
            testDataDirectories.files
                .asSequence()
                .filter(File::isDirectory)
                .flatMap(File::walkTopDown)
                .filter(File::isFile)
                .filter { it.extension in DumpExtensions }
                .filter { it.path.notContain("TODO", ignoreCase = true) }
                .filter { it.path.notContain("DISABLED", ignoreCase = true) }
                .toList()

        for (dump in dumps) dump.delete()
    }

    public companion object {
        public const val NAME: String = "deleteCompilerTextTestFiles"
    }
}

private val DumpExtensions: Set<String> = setOf("txt", "patch")
