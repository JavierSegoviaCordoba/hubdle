import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

hubdle {
    kotlin {
        jvm {
            // Used to check that it fails with the DevKit, which has its own functional tests
            if (providers.gradleProperty("withTestFunctional").isPresent) testFunctional()
        }
    }

    gradle {
        plugin {
            gradlePlugin {
                plugins {
                    create("example") {
                        id = "com.kotlin.devkit.example"
                        implementationClass = "com.kotlin.devkit.ExampleGradlePlugin"
                    }
                }
            }

            compilerPluginDevKit { //
                compilerPlugin(projects.exampleCompiler)
            }
        }
    }
}

afterEvaluate {
    val kotlin = extensions.getByType<KotlinJvmProjectExtension>()
    for (sourceSet in kotlin.sourceSets.sortedBy { it.name }) {
        fun Set<File>.relative(): List<String> =
            map { it.relativeTo(projectDir).invariantSeparatorsPath }.sorted()
        val kotlinDirs = sourceSet.kotlin.srcDirs.relative()
        val resourcesDirs = sourceSet.resources.srcDirs.relative()
        println("SOURCE_SET ${sourceSet.name} kotlin=$kotlinDirs resources=$resourcesDirs")
    }
}
