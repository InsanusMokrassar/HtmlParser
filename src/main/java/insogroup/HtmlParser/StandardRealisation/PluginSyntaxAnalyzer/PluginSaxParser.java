package insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer;

import insogroup.HtmlParser.StandardRealisation.exceptions.PluginException;
import insogroup.HtmlParser.StandardRealisation.settings.classes.Settings;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class PluginSaxParser extends DefaultHandler{

    private String spaces;
    private String tab;

    private Settings settings;

    private State currentState;

    public PluginSaxParser(Settings settings) {
        this.settings = settings;
        spaces = "";
        tab = "    ";
    }

    @Override
    public void startDocument(){
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attr){
        spaces += tab;
        try {
            currentState = currentState.addChild(qName, attr);
        }
        catch (NullPointerException e){
            currentState = new State(null, qName, attr, settings);
        }
    }

    @Override
    public void characters(char[] text, int start, int length){
        String stringText = "";
        for (int i = start; i< start + length; i++)
            stringText += text[i];
        currentState.setText(stringText);
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        spaces = spaces.replaceFirst(tab, "");
        try{
            currentState = currentState.getParent();
        }
        catch (PluginException e){}
    }

    @Override
    public void endDocument(){
//        mediator.print("End parsing plugin, thank you.");
    }

    public State getRootState(){
        State res = currentState;
        while (true){
            try{
                res = res.getParent();
            }
            catch (PluginException e){
                break;
            }
        }
        return res;
    }

}
