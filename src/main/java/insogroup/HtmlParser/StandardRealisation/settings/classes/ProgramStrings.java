package insogroup.HtmlParser.StandardRealisation.settings.classes;

import insogroup.HtmlParser.StandardRealisation.settings.interfaces.ProgramParameters;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashMap;

public class ProgramStrings implements ProgramParameters{
    HashMap<String, String> strings;


    private File stringsFile;

    public ProgramStrings(File settingsFile){
        this.stringsFile = settingsFile;
    }

    public void init(){
        strings = new HashMap<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(stringsFile, new SettingsSaxParser(this));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String get(String key){
        return strings.get(key);
    }

    public void addParameter(String key, String value){
        strings.put(key, value);
    }

}
