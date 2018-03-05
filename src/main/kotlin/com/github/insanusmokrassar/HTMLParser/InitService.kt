package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream

const val RESOURCES_PATH = "src/main/res/"
const val SETTINGS_PATH = RESOURCES_PATH + "settings.json"

val defaultSettings: Settings = File(SETTINGS_PATH).inputStream().readIObject()

fun getProperties(path: String): IInputObject<String, Any> {
    return readIObject(path)
}

internal fun openInputStream(path: String): InputStream {
    return ClassLoader.getSystemResourceAsStream(path) ?: FileInputStream(path)
}

@Throws(FileNotFoundException::class)
internal fun readIObject(path: String): IInputObject<String, Any> {
    return openInputStream(path).readIObject()
}
