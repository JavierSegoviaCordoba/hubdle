package com.javiersc.gradle.plugins.docs.tasks

import java.io.File
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity

data class ProjectInfo(
    @Input val name: String,
    @Input val projectPath: String,
    @get:PathSensitive(PathSensitivity.ABSOLUTE) @InputFile val mdFile: File,
) {

    @get:Input
    val filePath: String
        get() = projectPath.split(':').filter(String::isNotEmpty).joinToString("/")
}

internal val Project.projectsInfo: List<ProjectInfo>
    get() =
        subprojects.sortedBy(Project::getPath).mapNotNull { project ->
            val mdFiles =
                project.projectDir.walkTopDown().maxDepth(1).filter { file ->
                    file.name.endsWith(".md", true)
                }
            val mdFile =
                mdFiles.find { file -> file.name.contains("MODULE", true) }
                    ?: mdFiles.find { file -> file.name.contains("README", true) }
                        ?: mdFiles.firstOrNull()

            if (mdFile != null) {
                ProjectInfo(project.name, project.path, mdFile)
            } else {
                logger.lifecycle(
                    "${project.name} hasn't a markdown file, so it won't be added to docs"
                )
                null
            }
        }
