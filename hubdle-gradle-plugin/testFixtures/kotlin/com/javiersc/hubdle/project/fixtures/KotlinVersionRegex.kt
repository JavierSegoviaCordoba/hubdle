package com.javiersc.hubdle.project.fixtures

const val KotlinVersionRegexOrEmptyOrNull = """(^$?)|(null)|(null)|(\d+\.\d+\.\d+)"""

internal const val KotlinDevVersionRegexOrEmptyOrNull =
    """(^$?)|(null)|(\d+\.\d+\.\d+)(\-)(dev)(\-)(\d+)"""
