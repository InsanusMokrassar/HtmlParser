package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer.PluginException
import com.github.insanusmokrassar.HTMLParser.SiteSyntaxAnalyzer.SiteParser
import com.github.insanusmokrassar.IObjectK.interfaces.IObject

fun main(args: Array<String>) {
    println(getStrings().get<String>("app-name"))
    val property = try {
        getProperties(args[0])
    } catch (e: ArrayIndexOutOfBoundsException) {
        getProperties(
                defaultSettings["standard-property-path"]
        )
    }

    try {
        val pluginStateRoot = defaultSettings.getPlugin(property.get<String>("file")) ?: return
        val link = pluginStateRoot["site"]
        val content = SiteParser(defaultSettings).parse(link, pluginStateRoot)
        println(content.toString())
    } catch (e: PluginException) {
        e.printStackTrace()
    }
}
