package com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.exceptions.PluginException
import org.xml.sax.Attributes

import java.util.HashMap

interface State {

    val parent: State?

    val tag: String

    var text: String

    val childs: List<State>

    val root: State

    val attributes: Map<String, String>

    fun addChild(name: String, attr: Attributes): State

    @Throws(PluginException::class)
    operator fun get(key: String): String
}
