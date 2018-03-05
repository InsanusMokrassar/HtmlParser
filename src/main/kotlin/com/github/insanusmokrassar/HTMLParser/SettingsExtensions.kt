package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer.PluginException
import com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer.PluginSaxParser
import com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer.PluginState
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory

typealias Settings = IInputObject<String, Any>

val defaultSettings: Settings = readIObject("settings.json")

private fun Settings.checkWithRegExp(text: String, templateName: String): Boolean {
    return try {
        Regex(this[templateName]).matches(text)
    } catch (e: Exception) {
        false
    }
}

fun Settings.checkStandardRegexp(text: String): Boolean {
    return checkWithRegExp(text, "possible-text-characters")
}

fun Settings.checkVariable(text: String): Boolean {
    return checkWithRegExp(text, "variable-template")
}

fun Settings.getVariableName(text: String): String {
    return getVariableName(text, "possible-variable-characters")
}

private fun Settings.getVariableName(text: String, template: String): String {
    var res = ""
    try {
        text.toCharArray().forEach {
            if (checkWithRegExp(it.toString(), template)) {
                res += it
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return res
}


@Throws(PluginException::class)
fun Settings.getPlugin(pluginName: String): PluginState? {
    return getPlugin(openInputStream(pluginName))
}

@Throws(PluginException::class)
fun Settings.getPlugin(pluginInputStream: InputStream): PluginState? {
    val pluginParser = PluginSaxParser(this)
    val factory = SAXParserFactory.newInstance()
    try {
        val parser = factory.newSAXParser()
        parser.parse(pluginInputStream, pluginParser)
        return pluginParser.rootState
    } catch (e: Exception) {
        throw PluginException("Sorry, but i can't load plugin from the error : " + e.message)
    }
}
