package com.github.insanusmokrassar.HtmlParser.PluginSyntaxAnalyzer

import com.github.insanusmokrassar.HtmlParser.Settings
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

/**
 * Realisation of JSoup DefaultHandler for parsing plugins
 */
class PluginSaxParser(private val settings: Settings) : DefaultHandler() {

    private var currentState: PluginState? = null

    val rootState: PluginState
        get() = currentState?.root ?: throw IllegalStateException("Parser was not applied")

    override fun startElement(uri: String, localName: String?, qName: String, attr: Attributes) {
        currentState = currentState?.addChild(qName, attr) ?: PluginState(null, qName, attr, settings)
    }

    override fun characters(text: CharArray, start: Int, length: Int) {
        var stringText = ""
        for (i in start until start + length)
            stringText += text[i]
        currentState?.text = stringText
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        try {
            currentState?.parent?.let {
                currentState = it
            }
        } catch (e: PluginException) {
        }
    }
}
