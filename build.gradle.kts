plugins {
    `javiersc-versioning`
    `javiersc-all-projects`
    `javiersc-changelog`
    `javiersc-code-analysis`
//    `javiersc-code-coverage`
    `javiersc-code-formatter`
    `javiersc-docs`
    `kotlinx-binary-compatibility-validator`
    `javiersc-nexus`
    `javiersc-readme-badges-generator`
    `accessors-generator`
    `build-itself`
}

docs {
    navigation {
        reports {
            codeCoverage.set(false)
        }
    }
}

readmeBadges {
    coverage.set(false)
}
