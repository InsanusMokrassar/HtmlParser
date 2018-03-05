package com.github.insanusmokrassar.HTMLParser

fun main(args: Array<String>) {
    val pluginStateRoot = defaultSettings.getPlugin(
            if (args.isNotEmpty()) {
                args[0]
            } else {
                "plugins/example.xml"
            }
    )
    init(defaultPluginStateRoot = pluginStateRoot).parse(
            pluginStateRoot["site"]
    ).apply {
        println(this)
    }
}
