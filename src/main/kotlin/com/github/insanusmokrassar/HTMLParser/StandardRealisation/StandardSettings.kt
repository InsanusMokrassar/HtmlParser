package com.github.insanusmokrassar.HTMLParser.StandardRealisation

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.PluginSaxParser
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.exceptions.PluginException
import com.github.insanusmokrassar.IObjectK.interfaces.IObject
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory

typealias Settings = IObject<Any>

private fun Settings.checkWithRegExp(text: String, templateName: String): Boolean {
    return try {
        Regex(this[templateName]).matches(text)
    } catch (e: Exception) {
        false
    }

}

val Settings.visibleCount: Int?
    get() {
        return try {
            Integer.parseInt(this["count"])
        } catch (e: Throwable) {
            Integer.parseInt(this["standard-count"])
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
fun Settings.getPlugin(pluginName: String): State? {
    return getPlugin(FileInputStream(pluginName))
}

@Throws(PluginException::class)
fun Settings.getPlugin(pluginInputStream: InputStream): State? {
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
