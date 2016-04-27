package insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces;

import insogroup.HtmlParser.StandardRealisation.exceptions.PluginException;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.List;

public interface State {

    State addChild(String name, Attributes attr);

    String get(String key) throws PluginException;

    State getParent() throws PluginException;

    String getTag();

    String getText();

    void setText(String text);

    List<State> getChilds();

    State getRoot();

    HashMap<String, String> getAttributes();
    
}
