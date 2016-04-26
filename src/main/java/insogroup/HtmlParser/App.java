package insogroup.HtmlParser;

import insogroup.HtmlParser.StandardRealisation.ContentLoader;
import insogroup.HtmlParser.StandardRealisation.InitService.InitService;
import insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer.State;
import insogroup.HtmlParser.StandardRealisation.SiteSyntaxAnalyzer.SiteParser;
import insogroup.HtmlParser.StandardRealisation.exceptions.NetException;
import insogroup.HtmlParser.StandardRealisation.exceptions.PluginException;
import insogroup.HtmlParser.StandardRealisation.settings.classes.ProgramProperties;
import insogroup.HtmlParser.StandardRealisation.settings.classes.ProgramStrings;
import insogroup.HtmlParser.StandardRealisation.settings.classes.Settings;

import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Settings settings = InitService.getSettings();
        ProgramProperties property = null;
        try {
            property = InitService.getProperties(args[0]);
        }
        catch (ArrayIndexOutOfBoundsException e){
            args = new String[1];
            args[0] = settings.get("standard-property-path");
            property = InitService.getProperties(args[0]);
        }
        try {
            State stateTree = InitService.getPlugin(settings, property.get("file"));
            String link = stateTree.get("site");
            List<HashMap<String, String>> content = ContentLoader.loadContent(link, new SiteParser(settings), stateTree);
            System.out.println(content.toString());
        } catch (PluginException e) {
            e.printStackTrace();
        } catch (NetException e) {
            e.printStackTrace();
        }
    }
}
