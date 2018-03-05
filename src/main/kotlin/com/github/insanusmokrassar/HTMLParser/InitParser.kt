package com.github.insanusmokrassar.HTMLParser

import com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer.PluginState
import com.github.insanusmokrassar.HTMLParser.SiteSyntaxAnalyzer.SiteParser
import com.github.insanusmokrassar.IObjectK.interfaces.IInputObject
import com.github.insanusmokrassar.IObjectKRealisations.readIObject
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.URL

@Throws(IOException::class)
internal fun openInputStream(path: String): InputStream {
    return ClassLoader.getSystemResourceAsStream(path) ?: FileInputStream(path)
}

@Throws(FileNotFoundException::class)
internal fun readIObject(path: String): IInputObject<String, Any> {
    return openInputStream(path).readIObject()
}

fun init(
        settings: Settings = defaultSettings,
        defaultPluginStateRoot: PluginState? = null
): SiteParser {
    return SiteParser(
            settings,
            defaultPluginStateRoot
    )
}

fun init(
        settings: Settings = defaultSettings,
        defaultPluginURL: URL
): SiteParser {
    return init(settings, settings.getPlugin(defaultPluginURL.openStream()))
}

fun init(
        settings: Settings = defaultSettings,
        defaultPluginPath: String
): SiteParser {
    return init(settings, URL(defaultPluginPath))
}
