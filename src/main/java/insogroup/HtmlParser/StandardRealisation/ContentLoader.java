package insogroup.HtmlParser.StandardRealisation;

import insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State;
import insogroup.HtmlParser.StandardRealisation.SiteSyntaxAnalyzer.SiteParser;
import insogroup.HtmlParser.StandardRealisation.exceptions.NetException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ContentLoader {

    /**
     * @param link
     * @param siteParser
     * SiteParserExemplare
     * @param stateRoot
     * <pre>
     * <root site="PATH TO SITE" terms="TERMS FOR SEARCHING">
     *     <FIRST_TREE></FIRST_TREE>
     *     <SECOND_TREE></SECOND_TREE>
     *     ...
     * </root>
     * </pre>
     * @return List with elements which have values
     * @throws NetException
     */

    public static List<HashMap<String, String>> loadContent(String link,
                                                            SiteParser siteParser,
                                                            State stateRoot) throws NetException {
        List<HashMap<String, String>> resResponse = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            resResponse = siteParser.parse(doc.children(), stateRoot);
            if (resResponse.isEmpty()){
                throw new NetException("");
            }
        }
        catch (Exception e){
        }
        return resResponse;
    }


}
