package com.github.insanusmokrassar.HTMLParser.StandardRealisation.SiteSyntaxAnalyzer

import org.jsoup.nodes.Attribute
import org.jsoup.nodes.Attributes

import java.util.ArrayList
import java.util.HashMap

class ElementOfSite {
    var tag: String? = null
    var text: String? = null
    var attrs: HashMap<String, String>? = null
    var parent: ElementOfSite? = null
    private var childs: MutableList<ElementOfSite>? = null

    constructor(parent: ElementOfSite, tag: String, text: String, attrs: HashMap<String, String>) {
        this.parent = parent
        this.tag = tag
        this.text = text
        this.attrs = attrs
        childs = ArrayList()
    }

    constructor(parent: ElementOfSite, tag: String, text: String, attrs: Attributes) {
        this.parent = parent
        this.tag = tag
        this.text = text
        setAttrs(attrs)
        childs = ArrayList()
    }

    fun setAttrs(attrs: Attributes) {
        for ((key, value) in attrs) {
            this.attrs!![key] = value
        }
    }

    fun getChilds(): List<ElementOfSite>? {
        return childs
    }

    fun addChild(newChild: ElementOfSite) {
        childs!!.add(newChild)
    }
}
