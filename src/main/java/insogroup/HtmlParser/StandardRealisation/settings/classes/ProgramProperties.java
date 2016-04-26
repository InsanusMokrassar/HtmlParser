package insogroup.HtmlParser.StandardRealisation.settings.classes;

import insogroup.HtmlParser.StandardRealisation.settings.interfaces.ProgramParameters;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

public class ProgramProperties implements ProgramParameters {
    HashMap<String, String> properties;


    private String propertyPath;

    public ProgramProperties(String propertyPath){
        this.propertyPath = propertyPath;
        this.properties = new HashMap<>();
    }

    public void init(){
        try{
            Properties properties = new Properties();
            properties.load(new FileInputStream(propertyPath));
            for (Object key : properties.keySet()){
                this.properties.put(key.toString(), properties.get(key).toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String get(String key){
        return properties.get(key);
    }

    public void addParameter(String key, String value){
        properties.put(key, value);
    }


}
