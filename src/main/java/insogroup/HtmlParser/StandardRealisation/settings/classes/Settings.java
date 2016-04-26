package insogroup.HtmlParser.StandardRealisation.settings.classes;

import insogroup.HtmlParser.StandardRealisation.settings.interfaces.ProgramParameters;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings implements ProgramParameters {

    private HashMap<String, String> settings;


    public void init(File settingsFile){
        settings = new HashMap<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(settingsFile, new SettingsSaxParser(this));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(HashMap<String, String> inputSettings) {
        settings = inputSettings;
    }

    private boolean checkWithRegExp(String text, String template){
        try {
            String regex = settings.get(template);
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(text);
            return m.matches();
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean checkStardardRegexp(String text){
        return checkWithRegExp(text, "possible-text-characters");
    }

    public boolean checkVariable(String text){
        return checkWithRegExp(text, "variable-template");
    }

    public String getVariableName(String text){
        return getVariableName(text, "possible-variable-characters");
    }

    private String getVariableName(String text, String template){
        String res = "";
        try {
            for (char tmp : text.toCharArray()){
                if (checkWithRegExp("" + tmp, template)){
                    res += tmp;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public Integer getVisibleCount(){
        try{
            return Integer.parseInt(settings.get("count"));
        }
        catch (Exception e){
            return Integer.parseInt(settings.get("standard-count"));
        }
    }

    public void addParameter(String key, String value){
        settings.put(key, value);
    }

    public String get(String key){
        return settings.get(key);
    }

}
