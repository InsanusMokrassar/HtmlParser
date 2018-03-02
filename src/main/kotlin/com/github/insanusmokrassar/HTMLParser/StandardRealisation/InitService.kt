package com.github.insanusmokrassar.HTMLParser.StandardRealisation

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.PluginSaxParser
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.exceptions.PluginException
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import com.github.insanusmokrassar.IObjectK.realisations.SimpleTypedIObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import com.github.insanusmokrassar.IObjectKRealisations.toStringMap

import javax.xml.parsers.SAXParserFactory
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

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

@Throws(PluginException::class)
fun getPlugin(settings: Settings, pluginName: String): State? {
    val actualPlugin = "$PLUGINS_PATH$pluginName.xml"
    val pluginParser = PluginSaxParser(settings)
    val factory = SAXParserFactory.newInstance()
    try {
        val parser = factory.newSAXParser()
        parser.parse(File(actualPlugin), pluginParser)
        return pluginParser.rootState
    } catch (e: Exception) {
        throw PluginException("Sorry, but i can't load plugin from the error : " + e.message)
    }

}

@Throws(PluginException::class)
fun getPlugin(settings: Settings, pluginInputStream: InputStream): State? {
    val pluginParser = PluginSaxParser(settings)
    val factory = SAXParserFactory.newInstance()
    try {
        val parser = factory.newSAXParser()
        parser.parse(pluginInputStream, pluginParser)
        return pluginParser.rootState
    } catch (e: Exception) {
        throw PluginException("Sorry, but i can't load plugin from the error : " + e.message)
    }

}
