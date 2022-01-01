package com.javiersc.gradle.plugins.core

// TODO: delete when `javiersc-lib` adds them

fun List<String>.removeDuplicateEmptyLines(): String = reduce { acc: String, b: String ->
    if (acc.lines().lastOrNull().isNullOrBlank() && b.isBlank()) acc else "$acc\n$b"
}

fun String.removeDuplicateEmptyLines(): String = lines().removeDuplicateEmptyLines()

fun String.endWithNewLine(): String = if (lines().lastOrNull().isNullOrBlank()) this else "$this\n"
