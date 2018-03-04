package com.github.insanusmokrassar.HTMLParser.StandardRealisation

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.SiteSyntaxAnalyzer.SiteParser
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.exceptions.NetException
import org.jsoup.Jsoup
import java.util.*

/**
 * @param link
 * @param siteParser
 * SiteParserExemplare
 * @param pluginStateRoot
 * <pre>
 * <root site="PATH TO SITE" terms="TERMS FOR SEARCHING">
 * <FIRST_TREE></FIRST_TREE>
 * <SECOND_TREE></SECOND_TREE>
 * ...
</root> *
</pre> *
 * @return List with elements which have values
 * @throws NetException
 */

fun loadContent(
        link: String,
        siteParser: SiteParser,
        pluginStateRoot: State
): List<HashMap<String, String>> {
    var resResponse: List<HashMap<String, String>> = ArrayList()
    try {
        val doc = Jsoup.connect(link).get()
        resResponse = siteParser.parse(doc.children(), pluginStateRoot)
        if (resResponse.isEmpty()) {
            throw NetException("")
        }
    } catch (e: Exception) {
    }

    return resResponse
}
