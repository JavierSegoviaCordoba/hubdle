package com.javiersc.hubdle._utils

import java.io.File

internal fun resourceFile(resource: String): File =
    File(Thread.currentThread().contextClassLoader?.getResource(resource)?.toURI()!!)
