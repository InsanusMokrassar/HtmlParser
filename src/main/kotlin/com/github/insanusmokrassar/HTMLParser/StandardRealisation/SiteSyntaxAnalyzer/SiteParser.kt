package com.github.insanusmokrassar.HTMLParser.StandardRealisation.SiteSyntaxAnalyzer

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.Settings
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.checkVariable
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.getVariableName
import org.jsoup.select.Elements
import java.util.*

class SiteParser(
        private var actualSettings: Settings
) {

    fun parse(rootParsing: Elements, rootState: State): List<HashMap<String, String>> {
        val res = ArrayList<HashMap<String, String>>()
        for (tmpState in rootState.childs) {
            res.addAll(parseElement(rootParsing, tmpState, ArrayList()))
        }
        return res
    }

    private fun parseElement(rootParsing: Elements, currentState: State, oldHashMap: List<HashMap<String, String>>): List<HashMap<String, String>> {
        var tmpElements = findElements(rootParsing, currentState)
        var tmpValues: MutableList<HashMap<String, String>> = findVariables(tmpElements, currentState)
        var oldHashMap = updateHashMapValues(tmpValues, oldHashMap)
        currentState.childs.forEach {
            tmpElements = findElements(tmpElements, it)
            tmpValues = findVariables(tmpElements, it)
            oldHashMap = updateHashMapValues(tmpValues, oldHashMap)
            oldHashMap = parseElement(tmpElements, it, oldHashMap)
            rootParsing.removeAll(tmpElements)
        }
        return oldHashMap
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

    private fun findVariables(elements: Elements, state: State): MutableList<HashMap<String, String>> {
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
        for (tmp in elements) {
            val tmpValues = HashMap<String, String>()
            if (actualSettings.checkVariable(state.text)) {//nail
                val key = actualSettings.getVariableName(state.text)
                val value = tmp.ownText()
                if (value.isEmpty()) {
                    continue
                }
                tmpValues[key] = value
            }
            for (i in variableKeys.indices) {
                try {
                    val value = tmp.attributes().get(variableKeys[i])
                    if (value.isEmpty()) {
                        continue
                    }
                    tmpValues[variableNames[i]] = value
                } catch (e: Exception) {
                }

            }
            if (!tmpValues.isEmpty()) {
                variables.add(tmpValues)
            }
        }
        return variables
    }

    private fun findElements(start: Elements, state: State): Elements {
        var tmpElements = start.select(state.tag)//all attributes with key
        val attrs = state.attributes
        for (key in attrs.keys) {
            attrs[key] ?.let {
                value ->
                if (actualSettings.checkVariable(value)) {
                    tmpElements = tmpElements.select("[$key]")//all elements with attribute [key]
                    return@let
                }
                tmpElements = tmpElements.select("[$key*=\"$value\"]")//all elements with attribute [key] and value [value]
            }
        }
        return tmpElements
    }

}
