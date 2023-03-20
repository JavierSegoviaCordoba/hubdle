package com.javiersc.hubdle.project.fixtures

import java.io.File

fun resourceFile(resource: String): File =
    File(Thread.currentThread().contextClassLoader?.getResource(resource)?.toURI()!!)
