package com.javiersc.hubdle.project._utils

import java.io.File

internal fun resourceFile(resource: String): File =
    File(Thread.currentThread().contextClassLoader?.getResource(resource)?.toURI()!!)
