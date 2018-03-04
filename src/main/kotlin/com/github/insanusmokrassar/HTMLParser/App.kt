package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.SiteSyntaxAnalyzer.SiteParser
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.defaultSettings
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.exceptions.PluginException
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.getPlugin
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.getProperties
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.loadContent
import com.github.insanusmokrassar.IObjectK.interfaces.IObject

fun main(args: Array<String>) {
    val property: IObject<Any> = try {
        getProperties(args[0])
    } catch (e: ArrayIndexOutOfBoundsException) {
        getProperties(
                defaultSettings["standard-property-path"]
        )
    }

    try {
        val pluginStateRoot = defaultSettings.getPlugin(property.get<String>("file")) ?: return
        val link = pluginStateRoot["site"]
        val content = loadContent(link, SiteParser(defaultSettings), pluginStateRoot)
        println(content.toString())
    } catch (e: PluginException) {
        e.printStackTrace()
    }
}
