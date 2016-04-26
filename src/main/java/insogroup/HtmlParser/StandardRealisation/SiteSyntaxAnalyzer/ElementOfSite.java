package insogroup.HtmlParser.StandardRealisation.SiteSyntaxAnalyzer;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ElementOfSite {
    private String tag;
    private String text;
    private HashMap<String, String> attrs;
    private ElementOfSite parent;
    private List<ElementOfSite> childs;

    public ElementOfSite(ElementOfSite parent, String tag, String text, HashMap<String, String> attrs) {
        this.parent = parent;
        this.tag = tag;
        this.text = text;
        this.attrs = attrs;
        childs = new ArrayList<>();
    }

    public ElementOfSite(ElementOfSite parent, String tag, String text, Attributes attrs) {
        this.parent = parent;
        this.tag = tag;
        this.text = text;
        setAttrs(attrs);
        childs = new ArrayList<>();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HashMap<String, String> getAttrs() {
        return attrs;
    }

    public void setAttrs(HashMap<String, String> attrs) {
        this.attrs = attrs;
    }

    public void setAttrs(Attributes attrs) {
        for (Attribute tmp : attrs){
            this.attrs.put(tmp.getKey(), tmp.getValue());
        }
    }

    public ElementOfSite getParent() {
        return parent;
    }

    public List<ElementOfSite> getChilds() {
        return childs;
    }

    public void addChild(ElementOfSite newChild){
        childs.add(newChild);
    }

    public void setParent(ElementOfSite parent) {
        this.parent = parent;
    }
}
