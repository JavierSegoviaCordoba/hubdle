package com.javiersc.hubdle.settings.tasks

import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.mapProperty

@CacheableTask
public open class GenerateHubdleCatalogTask
@Inject
constructor(
    objects: ObjectFactory,
    layout: ProjectLayout,
) : DefaultTask() {

    @Input
    public val libraries: MapProperty<String, String> =
        objects.mapProperty<String, String>().convention(emptyMap())

    @OutputFile
    public val catalog: RegularFileProperty =
        objects
            .fileProperty()
            .convention(layout.buildDirectory.file("catalogs/hubdle.libs.versions.toml"))

    @TaskAction
    public fun run() {
        val content =
            buildList {
                    add("[libraries]")
                    for ((alias, groupArtifactVersion) in libraries.get()) {
                        add("""$alias = "$groupArtifactVersion"""")
                    }
                    add("")
                }
                .joinToString("\n")
        catalog.get().asFile.apply {
            parentFile.mkdirs()
            createNewFile()
            writeText(content)
        }
    }
}
