package insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State;
import insogroup.HtmlParser.StandardRealisation.exceptions.PluginException;
import insogroup.HtmlParser.StandardRealisation.settings.interfaces.Settings;
import org.xml.sax.Attributes;


public class StandardState implements State {
    protected State parent;
    protected List<State> childs;

    protected Settings settings;

    protected String tag;
    protected String text;

    protected HashMap<String, String> attributes;

    public StandardState(State parent, String tag, Attributes attributes, Settings settings) {
        this.parent = parent;
        this.tag = tag;
        this.settings = settings;
        setAttributes(attributes);
        this.childs = new ArrayList<>();
    }

    protected void setAttributes(Attributes attributes){
        this.attributes = new HashMap<>();
        for (int i = 0; i < attributes.getLength(); i++){
            String key = attributes.getQName(i);
            String value = attributes.getValue(i);
            this.attributes.put(key, value);
        }
    }

    public State addChild(String name, Attributes attr){
        State childState = new StandardState(this, name, attr, settings);
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
            if (!settings.checkStandardRegexp("" + tmp)){
                continue;
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
