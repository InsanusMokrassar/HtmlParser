package com.github.insanusmokrassar.HTMLParser.StandardRealisation

import com.github.insanusmokrassar.HTMLParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.SiteSyntaxAnalyzer.SiteParser
import com.github.insanusmokrassar.HTMLParser.StandardRealisation.exceptions.NetException
import org.jsoup.Jsoup
import java.io.InputStream
import java.net.URI
import java.net.URL
import java.util.*
import java.util.logging.Logger

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
        val url = URL(link)
        val docRoot = Jsoup.parse(url.openStream(), Charsets.UTF_8.toString(), url.toURI().toString()).children()
        resResponse = siteParser.parse(docRoot, pluginStateRoot)
        if (resResponse.isEmpty()) {
            throw NetException("")
        }
    } catch (e: Exception) {
        Logger.getGlobal().throwing("Content loader", "loadContent", e)
    }

    return resResponse
}
