package com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces

import org.xml.sax.Attributes

interface State {

    val parent: State?

    val tag: String

    var text: String

    val childs: List<State>

    val root: State

    val attributes: Map<String, String>

    fun addChild(name: String, attr: Attributes): State

    operator fun get(key: String): String
}
