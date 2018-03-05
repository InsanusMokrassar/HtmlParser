package com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer

import com.github.insanusmokrassar.HTMLParser.Settings
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class PluginSaxParser(private val settings: Settings) : DefaultHandler() {

    private var spaces: String? = null
    private val tab: String

    private var currentState: PluginState? = null

    val rootState: PluginState?
        get() = currentState ?. root

    init {
        spaces = ""
        tab = "    "
    }

    override fun startElement(uri: String, localName: String?, qName: String, attr: Attributes) {
        spaces += tab
        currentState = currentState ?. addChild(qName, attr) ?: PluginState(null, qName, attr, settings)
    }

    override fun characters(text: CharArray?, start: Int, length: Int) {
        var stringText = ""
        for (i in start until start + length)
            stringText += text!![i]
        currentState ?. text = stringText
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        spaces = spaces!!.replaceFirst(tab.toRegex(), "")
        try {
            currentState ?. parent ?.let {
                currentState = it
            }
        } catch (e: PluginException) {
        }
    }
}
