package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleTypedIObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toStringMap
import java.io.FileInputStream
import java.io.FileNotFoundException

private val stringsObjectsMap: MutableMap<String, IInputObject<String, String>> = HashMap()

private fun initStringIObject(path: String = "strings.json"): IInputObject<String, String> {
    return try {
        readIObject(path)
    } catch (e: FileNotFoundException) {
        readIObject("strings.json")
    }.run {
        SimpleTypedIObject(toStringMap())
    }
}

fun getStrings(path: String = "strings.json"): IInputObject<String, String> {
    return stringsObjectsMap[path] ?: initStringIObject(path).apply {
        stringsObjectsMap[path] = this
    }
}
