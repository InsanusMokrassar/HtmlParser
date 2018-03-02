package com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.Settings
import org.xml.sax.Attributes

class PluginState(
        parent: State?,
        tag: String,
        attributes:
        Attributes,
        settings: Settings
) : StandardState(
        parent,
        tag,
        attributes,
        settings
), State {

    override fun addChild(name: String, attr: Attributes): State {
        val child = PluginState(this, name, attr, settings)
        childs.add(child)
        return child
    }
}
