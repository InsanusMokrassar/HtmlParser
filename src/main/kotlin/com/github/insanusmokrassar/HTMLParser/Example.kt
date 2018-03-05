package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.HTMLParser.SiteSyntaxAnalyzer.SiteParser

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
