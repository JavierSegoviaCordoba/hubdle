import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

plugins {
    id("com.diffplug.spotless")
}

configure<SpotlessExtension> {
    kotlin {
        target(
            "*/kotlin/**/*.kt",
            "src/*/kotlin/**/*.kt",
        )
        targetExclude(
            "*/resources/**/*.kt",
            "src/*/resources/**/*.kt",
            "**/build/**",
            "**/.gradle/**",
        )
        ktfmt(KTFMT_VERSION).kotlinlangStyle()
    }
}

val ideaDir = file("$rootDir/.idea").also(File::mkdirs)

file("${ideaDir.path}/ktfmt.xml").apply {
    if (!exists()) {
        createNewFile()
        writeText(ktfmtXmlContent)
    }
}

val ktfmtXmlContent: String
    get() =
        """
            <?xml version="1.0" encoding="UTF-8"?>
            <project version="4">
              <component name="KtfmtSettings">
                <option name="enabled" value="true" />
                <option name="uiFormatterStyle" value="Kotlinlang" />
              </component>
            </project>
        """.trimIndent()

allprojects {
    afterEvaluate {
        if (plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0) {
            plugins.apply("com.javiersc.gradle.plugins.code.formatter")
        }
    }
}
