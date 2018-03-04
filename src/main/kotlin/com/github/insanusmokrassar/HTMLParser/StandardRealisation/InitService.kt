package com.github.insanusmokrassar.HTMLParser.StandardRealisation

import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleTypedIObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toStringMap
import java.io.File
import java.io.FileInputStream

const val RESOURCES_PATH = "src/main/res/"
const val PLUGINS_PATH = RESOURCES_PATH + "plugins/"
const val SETTINGS_PATH = RESOURCES_PATH + "settings.json"

val defaultSettings: Settings = File(SETTINGS_PATH).inputStream().readIObject()

fun getStrings(path: String): IObject<String> {
    return FileInputStream(path).readIObject().run {
        SimpleTypedIObject(toStringMap())
    }
}

fun getProperties(path: String): IObject<Any> {
    return FileInputStream(path).readIObject()
}
