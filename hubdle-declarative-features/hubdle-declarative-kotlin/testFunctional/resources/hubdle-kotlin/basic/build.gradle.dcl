hubdle {
    kotlin {
        analysis {
            enabled = true
        }
        compilerOptions {
            apiVersion = "2.2"
            languageVersion = "2.2"
        }
        coverage {
            enabled = true
            jacoco = "0.8.13"
        }
        documentation {
            enabled = true
        }
        explicitApi {
            enabled = true
            mode = "Strict"
        }
        features {
            enabled = true
            atomicfu()
            buildKonfig()
            compose()
            coroutines()
            extendedStdlib()
            kopy {
                enabled = true
                function("Copy")
                visibility = "Auto"
            }
            kotest()
            molecule()
            powerAssert {
                enabled = true
                function("kotlin.assert")
            }
            serialization {
                enabled = true
                csv = true
                flf = true
                json = true
            }
            sqlDelight()
        }
        format {
            enabled = true
            exclude("build/**/*.kt")
            include("src/**/*.kt")
            ktfmtVersion = "0.56"
        }
        languageSettings {
            enableLanguageFeature("ContextParameters")
            optIn("kotlin.ExperimentalStdlibApi")
            optIn("kotlin.time.ExperimentalTime")
        }
        testing {
            enabled = true
            maxParallelForks = 2
            options = "JUnitPlatform"
            showStandardStreams = true
        }
    }
}
