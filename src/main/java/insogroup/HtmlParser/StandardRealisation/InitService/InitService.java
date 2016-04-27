package insogroup.HtmlParser.StandardRealisation.InitService;

import insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer.PluginSaxParser;
import insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State;
import insogroup.HtmlParser.StandardRealisation.exceptions.PluginException;
import insogroup.HtmlParser.StandardRealisation.settings.classes.ProgramProperties;
import insogroup.HtmlParser.StandardRealisation.settings.classes.ProgramStrings;
import insogroup.HtmlParser.StandardRealisation.settings.classes.StandardSettings;
import insogroup.HtmlParser.StandardRealisation.settings.interfaces.Settings;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class InitService {
    public static final String RESOURCES_PATH= "src/main/res/";
    public static final String PLUGINS_PATH= RESOURCES_PATH + "plugins/";
    public static final String SETTINGS_PATH= RESOURCES_PATH + "settings.xml";

    public static Settings getSettings(){
        StandardSettings settings = new StandardSettings();
        settings.init(new File(SETTINGS_PATH));
        return settings;
    }

    public static ProgramStrings getStrings(String path){
        ProgramStrings strings = new ProgramStrings(new File(path));
        strings.init();
        return strings;
    }

    public static ProgramProperties getProperties(String path){
        ProgramProperties properties = new ProgramProperties(path);
        properties.init();
        return properties;
    }

    public static State getPlugin(Settings settings, String pluginName) throws PluginException{
        String actualPlugin = PLUGINS_PATH + pluginName + ".xml";
        PluginSaxParser pluginParser = new PluginSaxParser(settings);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(actualPlugin), pluginParser);
            return pluginParser.getRootState();
        }
        catch (Exception e){
            throw new PluginException("Sorry, but i can't load plugin from tha error : " + e.getMessage());
        }
    }



}
