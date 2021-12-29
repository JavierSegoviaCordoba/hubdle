plugins {
    `javiersc-versioning`
    `javiersc-all-projects`
    `javiersc-changelog`
    `javiersc-code-analysis`
    id("org.jetbrains.kotlinx.kover") version "0.5.0-RC"// REMOVE THIS AND IT WORKS
    `javiersc-code-formatter`
    `javiersc-docs`
    `kotlinx-binary-compatibility-validator`
    `javiersc-nexus`
    `javiersc-readme-badges-generator`
    `accessors-generator`
}
