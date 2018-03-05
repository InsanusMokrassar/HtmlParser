package com.github.insanusmokrassar.HtmlParser

import com.github.insanusmokrassar.HtmlParser.SiteSyntaxAnalyzer.SiteParser

fun main(args: Array<String>) {
    val pluginStateRoot = defaultSettings.getPlugin(
            if (args.isNotEmpty()) {
                args[0]
            } else {
                "plugins/example.xml"
            }
    )
    SiteParser(
            defaultSettings,
            pluginStateRoot
    ).parse(
            pluginStateRoot["site"]
    ).apply {
        println(this)
    }
}
