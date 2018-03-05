package com.github.insanusmokrassar.HTMLParser.PluginSyntaxAnalyzer

/**
 * This exception must bew thrown when plugin was not initialized for fatal reason or in action which call error
 */
class PluginException(message: String, e: Throwable? = null) : Exception(message, e)
