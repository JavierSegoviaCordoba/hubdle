import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("com.github.ben-manes.versions")
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        !candidate.version.isStable
    }
}

internal val String.isStable: Boolean
    get() {
        val limit = "${properties["dependencyDiscoveryLimit"] ?: ""}"
        val filters =
                mutableListOf(
                        "SNAPSHOT",
                        "dev",
                        "eap",
                        "alpha",
                        "beta",
                        "(M|milestone)(\\.?)[0-9]+",
                        "rc"
                ).apply {
                    val indexToRemove =
                            if (limit == "milestone") indexOf("(M|milestone)(\\.?)[0-9]+") else indexOf(limit)
                    if (indexToRemove != -1) subList(indexToRemove, size).clear()
                }
        return !Regex(filters.joinToString("|"), RegexOption.IGNORE_CASE).containsMatchIn(this)
    }
