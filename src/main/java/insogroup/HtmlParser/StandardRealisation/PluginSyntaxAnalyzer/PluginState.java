package insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer;

import insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer.interfaces.State;
import insogroup.HtmlParser.StandardRealisation.settings.interfaces.Settings;
import org.xml.sax.Attributes;

public class PluginState extends StandardState implements State {
    public PluginState(State parent, String tag, Attributes attributes, Settings settings) {
        super(parent, tag, attributes, settings);
    }

    @Override
    public State addChild(String name, Attributes attr) {
        State child = new PluginState(this, name, attr, settings);
        childs.add(child);
        return child;
    }
}
