package com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.Settings
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.checkStandardRegexp
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.exceptions.PluginException
import org.xml.sax.Attributes
import java.util.*

class PluginState(
        override val parent: State?,
        override val tag: String,
        externalAttributes: Attributes,
        private val settings: Settings
) : State {
    override var text: String = ""
        set(value) {
            var res = ""
            value.toCharArray().forEach {
                if (settings.checkStandardRegexp("" + it)) {
                    res += it
                }
            }
            field = res
        }
    override val childs: MutableList<State> = ArrayList()

    override val attributes: Map<String, String> = (0 until externalAttributes.length).map {
        i ->
        Pair(externalAttributes.getQName(i), externalAttributes.getValue(i))
    }.toMap()

    override val root: State
        get() {
            var result: State = this
            while (true) {
                result = result.parent ?: return result
            }
        }

//    override fun addChild(name: String, attr: Attributes): State {
//        return PluginState(this, name, attr, settings).also {
//            childs.add(it)
//        }
//    }

    override fun addChild(name: String, attr: Attributes): State {
        val child = PluginState(this, name, attr, settings)
        childs.add(child)
        return child
    }

    override fun get(key: String): String {
        return attributes[key] ?: throw PluginException("Can't give plugin attribute: $key")
    }
}
