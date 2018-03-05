package com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer

import com.github.insanusmokrassar.HTMLParser.Settings
import com.github.insanusmokrassar.HTMLParser.checkStandardRegexp
import org.xml.sax.Attributes
import java.util.*

class PluginState(
        val parent: PluginState?,
        val tag: String,
        externalAttributes: Attributes,
        private val settings: Settings
) {
    var text: String = ""
        set(value) {
            var res = ""
            value.toCharArray().forEach {
                if (settings.checkStandardRegexp("" + it)) {
                    res += it
                }
            }
            field = res
        }
    val childs: MutableList<PluginState> = ArrayList()

    val attributes: Map<String, String> = (0 until externalAttributes.length).map {
        i ->
        Pair(externalAttributes.getQName(i), externalAttributes.getValue(i))
    }.toMap()

    val root: PluginState
        get() {
            var result: PluginState = this
            while (true) {
                result = result.parent ?: return result
            }
        }

    fun addChild(name: String, attr: Attributes): PluginState {
        val child = PluginState(this, name, attr, settings)
        childs.add(child)
        return child
    }

    operator fun get(key: String): String {
        return attributes[key] ?: throw PluginException("Can't give plugin attribute: $key")
    }
}
