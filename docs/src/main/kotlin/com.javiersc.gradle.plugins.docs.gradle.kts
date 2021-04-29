import com.javiersc.plugins.core.isSignificant
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

plugins {
    dokka
    mkdocs
}

allprojects {
    afterEvaluate {
        if (plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0) {
            plugins.apply("org.jetbrains.dokka")
            tasks {
                withType<DokkaTaskPartial>() {
                    dokkaSourceSets.configureEach { includes.from(listOf("MODULE.md")) }
                }
                withType<DokkaMultiModuleTask>().configureEach {
                    val dokkaDir = buildDir.resolve("dokka")
                    outputDirectory.set(dokkaDir)
                }
            }
        }
    }
}

tasks {
    withType<DokkaTaskPartial>() {
        dokkaSourceSets.configureEach { includes.from(listOf("MODULE.md")) }
    }

    withType<DokkaMultiModuleTask>().configureEach {
        val dokkaDir = buildDir.resolve("dokka")
        outputDirectory.set(dokkaDir)
    }

    register("buildDocs") {
        copy {
            from("$rootDir/.docs")
            into("$rootDir/build/.docs")
        }

        if (file("$rootDir/.docs/index.md").exists().not()) {
            copy {
                from("$rootDir/README.md")
                into("$rootDir/build/.docs/docs")
                rename { fileName -> fileName.replace(fileName, "index.md") }
            }
        }

        copyChangelogFileAndRemoveUnreleased()

        dependsOn("mkdocsBuild")

        doLast {
            if (isSignificant) {
                if (project.version.toString().endsWith("-SNAPSHOT")) {
                    copy {
                        from("$rootDir/build/dokka")
                        into("$rootDir/build/docs/_site/api/snapshots")
                    }
                } else {
                    file("$rootDir/build/docs/_site/api/index.html").apply {
                        ensureParentDirsCreated()
                        if (!exists()) createNewFile()
                        writeText(indexHtmlContent)
                    }

                    copy {
                        from("$rootDir/build/dokka")
                        into("$rootDir/build/docs/_site/api/versions/${project.version}")
                    }
                }
            }
        }
    }
}

configure<MkdocsExtension> {
    strict = false

    sourcesDir = "$rootDir/build/.docs"

    buildDir = "$rootDir/build/docs"

    publish.docPath = "_site"
}

val indexHtmlContent: String
    get() =
        """
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="refresh" content="0;URL='versions/${project.version}'" />
            </head>
            <body>
            </body>
            </html>
        """.trimIndent()

fun copyChangelogFileAndRemoveUnreleased() {
    if (file("$rootDir/CHANGELOG.md").exists()) {
        copy {
            from("$rootDir/CHANGELOG.md")
            into("$rootDir/build/.docs/docs")
        }

        File("$rootDir/build/.docs/docs/CHANGELOG.md").apply {
            val content = readLines()
            val firstVersionLine =
                    content.indexOfFirst { line ->
                        line.contains("## [") && !line.contains("[Unreleased]")
                    } - 1

            val contentUpdated =
                    (content.subList(0, 1) + content.subList(firstVersionLine, content.count()))
                            .joinToString("\n")
            writeText(contentUpdated)
        }
    }
}
