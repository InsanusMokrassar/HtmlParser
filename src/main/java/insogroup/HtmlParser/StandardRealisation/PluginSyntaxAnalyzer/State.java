package insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import insogroup.HtmlParser.StandardRealisation.exceptions.PluginException;
import insogroup.HtmlParser.StandardRealisation.settings.classes.Settings;

public class State {
    private State parent;
    private List<State> childs;

    private Settings settings;

    private String tag;
    private String text;

    private HashMap<String, String> attributes;

    public State(State parent, String tag, org.xml.sax.Attributes attributes, Settings settings) {
        this.parent = parent;
        this.tag = tag;
        this.settings = settings;
        setAttributes(attributes);
        this.childs = new ArrayList<>();
    }

    private void setAttributes(org.xml.sax.Attributes attributes){
        this.attributes = new HashMap<>();
        for (int i = 0; i < attributes.getLength(); i++){
            String key = attributes.getQName(i);
            String value = attributes.getValue(i);
            this.attributes.put(key, value);
        }
    }

    public State addChild(String name, org.xml.sax.Attributes attr){
        State childState = new State(this, name, attr, settings);
        childs.add(childState);
        return childState;
    }

    public String get(String key) throws PluginException{
        try{
            return attributes.get(key);
        }
        catch (Exception e){
            throw new PluginException("Can't give plugin attribute: " + key + ": " + e.getMessage());
        }
    }

    public State getParent() throws PluginException{
        if (parent == null){
            throw new PluginException("Is root");
        }
        return parent;
    }

    public String getTag() {
        return tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        String res = "";
        for (char tmp : text.toCharArray()){
            if (!settings.checkStardardRegexp("" + tmp)){
                break;
            }
            res += tmp;
        }
        this.text = res;
    }

    public List<State> getChilds() {
        return childs;
    }

    public State getRoot(){
        State res = this;
        try{
            while (true){
                res = res.getParent();
            }
        }
        catch (PluginException e){
            return res;
        }
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }



}
