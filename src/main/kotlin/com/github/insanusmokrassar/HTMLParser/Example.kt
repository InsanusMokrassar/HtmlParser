package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer.PluginSaxParser
import com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer.PluginState
import com.github.insanusmokrassar.HTMLParser.SiteSyntaxAnalyzer.SiteParser
import java.net.URL

fun main(args: Array<String>) {
    val pluginStateRoot = defaultSettings.getPlugin(
            if (args.isNotEmpty()) {
                args[0]
            } else {
                "plugins/example.xml"
            }
    ) ?: return
    init(defaultPluginStateRoot = pluginStateRoot).parse(
            pluginStateRoot["site"]
    ).apply {
        println(this)
    }
}
