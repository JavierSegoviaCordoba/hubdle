package com.javiersc.hubdle.extensions.config.documentation.readme._internal.models

/** Sonar metrics */
internal enum class Sonar(val label: String, val path: String) {
    Coverage("Coverage", "coverage"),
    Quality("Quality", "quality_gate"),
    TechDebt("Tech debt", "tech_debt"),
}
