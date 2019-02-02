package com.github.insanusmokrassar.HtmlParser

import com.github.insanusmokrassar.HtmlParser.SiteSyntaxAnalyzer.SiteParser

/**
 * It is just an example of work of parser
 *
 * first argument - parser location
 * second argument - html location
 */
fun main(args: Array<String>) {
    val pluginStateRoot = defaultSettings.getPlugin(
        args[0]
    )
    SiteParser(defaultSettings, pluginStateRoot).parse(
        args[1]
    ).apply {
        println(this)
    }
}
