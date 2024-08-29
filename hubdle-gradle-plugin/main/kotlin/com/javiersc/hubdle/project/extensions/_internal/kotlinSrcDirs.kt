package com.javiersc.hubdle.project.extensions._internal

import java.io.File
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal val Project.allKotlinSrcDirs: Provider<Set<File>>
    get() = provider {
        extensions
            .findByType<KotlinProjectExtension>()
            ?.sourceSets
            ?.flatMap { kotlinSourceSet -> kotlinSourceSet.kotlin.sourceDirectories }
            .orEmpty()
            .toSet()
    }

internal val Project.allKotlinSrcDirsWithoutBuild: Provider<Set<File>>
    get() =
        allKotlinSrcDirs.map { dirs ->
            val buildDir = layout.buildDirectory.asFile.get()
            dirs.filter { dir -> buildDir.absolutePath !in dir.absolutePath }.toSet()
        }

internal val Project.kotlinSrcDirs: Provider<Set<File>>
    get() =
        allKotlinSrcDirs.map { dirs ->
            dirs
                .filterNot { dir ->
                    val relativePath: String = dir.relativeTo(projectDir).path
                    val relativeDirs: List<String> = relativePath.split(File.separatorChar)
                    relativeDirs.any { it.endsWith("Test") || it == "test" }
                }
                .toSet()
        }

internal val Project.kotlinGeneratedSrcDirs: Provider<Set<File>>
    get() =
        kotlinSrcDirs.map { dirs ->
            val buildDir = layout.buildDirectory.asFile.get()
            dirs
                .filter { file -> buildDir.resolve("generated").absolutePath in file.absolutePath }
                .toSet()
        }

internal val Project.kotlinSrcDirsWithoutBuild: Provider<Set<File>>
    get() =
        kotlinSrcDirs.map { dirs ->
            dirs
                .filterNot { layout.buildDirectory.asFile.get().absolutePath in it.absolutePath }
                .toSet()
        }

internal val Project.kotlinTestsSrcDirs: Provider<Set<File>>
    get() =
        allKotlinSrcDirs.map { dirs ->
            dirs
                .filter { dir ->
                    val relativePath: String = dir.relativeTo(projectDir).path
                    val relativeDirs: List<String> = relativePath.split(File.separatorChar)
                    relativeDirs.any {
                        it.startsWith("test") || it.endsWith("Test") || it == "test"
                    }
                }
                .toSet()
        }

internal val Project.kotlinGeneratedTestSrcDirs: Provider<Set<File>>
    get() =
        kotlinTestsSrcDirs.map { dirs ->
            val buildDir = layout.buildDirectory.asFile.get()
            dirs
                .filter { dir -> buildDir.resolve("generated").absolutePath in dir.absolutePath }
                .toSet()
        }

internal val Project.kotlinTestsSrcDirsWithoutBuild: Provider<Set<File>>
    get() =
        kotlinTestsSrcDirs.map { dirs ->
            val buildDir = layout.buildDirectory.asFile.get()
            dirs.filter { dir -> buildDir.absolutePath !in dir.absolutePath }.toSet()
        }
