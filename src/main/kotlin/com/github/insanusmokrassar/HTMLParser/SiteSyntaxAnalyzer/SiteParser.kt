package com.github.insanusmokrassar.HTMLParser.SiteSyntaxAnalyzer

import com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer.PluginState
import com.github.insanusmokrassar.HTMLParser.Settings
import com.github.insanusmokrassar.HTMLParser.checkVariable
import com.github.insanusmokrassar.HTMLParser.getVariableName
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.net.ConnectException
import java.net.URL
import java.util.*
import java.util.logging.Logger

class SiteParser(
        private var actualSettings: Settings
) {
    fun parse(
            link: String,
            pluginStateRoot: PluginState
    ): List<HashMap<String, String>> {
        return parse(URL(link), pluginStateRoot)
    }

    @Throws(Exception::class)
    fun parse(
            url: URL,
            pluginStateRoot: PluginState
    ): List<HashMap<String, String>> {
        val docRoot = Jsoup.parse(
                url.openStream(),
                Charsets.UTF_8.toString(),
                url.toURI().toString()
        ).children()

        return parse(docRoot, pluginStateRoot).apply {
            if (isEmpty()) {
                throw ConnectException()
            }
        }
    }

    fun parse(rootParsing: Elements, rootState: PluginState): List<HashMap<String, String>> {
        val res = ArrayList<HashMap<String, String>>()
        for (tmpState in rootState.childs) {
            res.addAll(parseElement(rootParsing, tmpState, ArrayList()))
        }
        return res
    }

    private fun parseElement(rootParsing: Elements, currentState: PluginState, oldHashMap: List<HashMap<String, String>>): List<HashMap<String, String>> {
        var tmpElements = findElements(rootParsing, currentState)
        var tmpValues: MutableList<HashMap<String, String>> = findVariables(tmpElements, currentState)
        var newHashMap = updateHashMapValues(tmpValues, oldHashMap)
        currentState.childs.forEach {
            tmpElements = findElements(tmpElements, it)
            tmpValues = findVariables(tmpElements, it)
            newHashMap = updateHashMapValues(tmpValues, newHashMap)
            newHashMap = parseElement(tmpElements, it, newHashMap)
            rootParsing.removeAll(tmpElements)
        }
        return newHashMap
    }

    private fun updateHashMapValues(
            oldHashMap: MutableList<HashMap<String, String>>,
            newHashMaps: List<HashMap<String, String>>
    ): List<HashMap<String, String>> {
        newHashMaps.forEachIndexed {
            i, hashMap ->
            hashMap.keys.forEach {
                key ->
                hashMap[key] ?.let {
                    oldHashMap[i].put(key, it)
                } ?: oldHashMap.add(hashMap)
            }
        }
        return oldHashMap
    }

    private fun findVariables(elements: Elements, state: PluginState): MutableList<HashMap<String, String>> {
        val variableKeys = ArrayList<String>()
        val variableNames = ArrayList<String>()
        val variables = ArrayList<HashMap<String, String>>()
        val attrs = state.attributes
        attrs.keys.forEach {
            key ->
            attrs[key] ?.let {
                value ->
                if (actualSettings.checkVariable(value)) {
                    variableKeys.add(key)
                    variableNames.add(actualSettings.getVariableName(value))
                }
            }
        }
        elements.forEach {
            element ->
            val tmpValues = HashMap<String, String>()
            if (actualSettings.checkVariable(state.text)) {//nail
                val key = actualSettings.getVariableName(state.text)
                val value = element.ownText()
                if (value.isNotEmpty()) {
                    tmpValues[key] = value
                }
            }
            variableKeys.forEach {
                variableKey ->
                try {
                    val value = element.attributes().get(variableKey)
                    if (value.isNotEmpty()) {
                        tmpValues[variableKey] = value
                    }
                } catch (e: Exception) {
                }
            }
            if (tmpValues.isNotEmpty()) {
                variables.add(tmpValues)
            }
        }
        return variables
    }

    private fun findElements(start: Elements, state: PluginState): Elements {
        var tmpElements = start.select(state.tag)//all attributes with key
        val attrs = state.attributes
        attrs.forEach {
            tmpElements = tmpElements.select(
                    if (actualSettings.checkVariable(it.value)) {
                        "[${it.key}]"//all elements with attribute [key]
                    } else {
                        "[${it.key}*=\"${it.value}\"]"//all elements with attribute [key] and value [value]
                    }
            )
        }
        return tmpElements
    }

}
