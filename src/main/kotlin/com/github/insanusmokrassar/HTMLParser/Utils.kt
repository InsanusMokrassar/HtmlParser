package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

@Throws(IOException::class)
internal fun openInputStream(path: String): InputStream {
    return ClassLoader.getSystemResourceAsStream(path) ?: FileInputStream(path)
}

@Throws(FileNotFoundException::class)
internal fun readIObject(path: String): IInputObject<String, Any> {
    return openInputStream(path).readIObject()
}
