package com.github.insanusmokrassar.HTMLParser.StandardRealisation

import com.github.insanusmokrassar.IObjectK.interfaces.IObject

typealias Settings = IObject<Any>

private fun Settings.checkWithRegExp(text: String, templateName: String): Boolean {
    return try {
        Regex(this[templateName]).matches(text)
    } catch (e: Exception) {
        false
    }

}

val Settings.visibleCount: Int?
    get() {
        return try {
            Integer.parseInt(this["count"])
        } catch (e: Throwable) {
            Integer.parseInt(this["standard-count"])
        }
    }

fun Settings.checkStandardRegexp(text: String): Boolean {
    return checkWithRegExp(text, "possible-text-characters")
}

fun Settings.checkVariable(text: String): Boolean {
    return checkWithRegExp(text, "variable-template")
}

fun Settings.getVariableName(text: String): String {
    return getVariableName(text, "possible-variable-characters")
}

private fun Settings.getVariableName(text: String, template: String): String {
    var res = ""
    try {
        text.toCharArray().forEach {
            if (checkWithRegExp(it.toString(), template)) {
                res += it
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return res
}
