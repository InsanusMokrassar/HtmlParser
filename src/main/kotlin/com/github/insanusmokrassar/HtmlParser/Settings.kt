package com.github.insanusmokrassar.HtmlParser

import com.github.insanusmokrassar.HtmlParser.PluginSyntaxAnalyzer.*
import kotlinx.serialization.*
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory

@Serializable
data class Settings(
    @SerialName("possible-text-characters")
    @Optional
    private val possibleTextCharacters: String = "[\\w\\d\\%]+",
    @SerialName("variable-template")
    @Optional
    private val variableTemplate: String = "\\%\\%[^\\%]+\\%\\%",
    @SerialName("possible-variable-characters")
    @Optional
    private val possibleVariableCharacters: String = "[^\\%]+",
    @SerialName("strings-path")
    @Optional
    private val stringsPath: String = "strings.json",
    @SerialName("standard-property-path")
    @Optional
    private val standardPropertyPath: String = "standardProperty.properties"
) {
    @Transient
    private val possibleTextCharactersRegex = Regex(possibleTextCharacters)
    @Transient
    private val variableTemplateRegex = Regex(variableTemplate)
    @Transient
    private val possibleVariableCharactersRegex = Regex(possibleVariableCharacters)


    fun checkStandardRegexp(text: String): Boolean {
        return possibleTextCharactersRegex.matches(text)
    }

    fun checkVariable(text: String): Boolean {
        return variableTemplateRegex.matches(text)
    }

    fun getVariableName(text: String): String {
        return possibleVariableCharactersRegex.find(
            text
        )?.value ?: ""
    }

    fun getPlugin(pluginName: String): PluginState {
        return getPlugin(openInputStream(pluginName))
    }

    fun getPlugin(pluginInputStream: InputStream): PluginState {
        val pluginParser = PluginSaxParser(this)
        val factory = SAXParserFactory.newInstance()
        try {
            val parser = factory.newSAXParser()
            parser.parse(pluginInputStream, pluginParser)
            return pluginParser.rootState
        } catch (e: Exception) {
            throw PluginException("Sorry, but i can't load plugin: " + e.message)
        }
    }
}
