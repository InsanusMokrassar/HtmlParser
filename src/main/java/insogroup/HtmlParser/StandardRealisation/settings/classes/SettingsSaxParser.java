package insogroup.HtmlParser.StandardRealisation.settings.classes;

import insogroup.HtmlParser.StandardRealisation.settings.interfaces.ProgramParameters;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

public class SettingsSaxParser extends DefaultHandler{
    private ProgramParameters settings;
    private Stack<String> actualSetting;
    private String actualText;

    public SettingsSaxParser(ProgramParameters settings) {
        this.settings = settings;
        actualSetting = new Stack<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attr){
        actualSetting.push(qName);
        actualText = "";
    }

    @Override
    public void characters(char[] text, int start, int finish){
        for (int i = start; i < start + finish; i++){
            actualText += text[i];
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        settings.addParameter(actualSetting.pop(), actualText);
    }
}
