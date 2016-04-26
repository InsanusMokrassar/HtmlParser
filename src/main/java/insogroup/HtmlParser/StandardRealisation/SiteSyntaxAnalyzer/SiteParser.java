package insogroup.HtmlParser.StandardRealisation.SiteSyntaxAnalyzer;

import insogroup.HtmlParser.StandardRealisation.PluginSyntaxAnalyzer.State;
import insogroup.HtmlParser.StandardRealisation.settings.classes.Settings;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SiteParser {

    Settings actualSettings;

    public SiteParser(Settings settings){
        this.actualSettings = settings;
    }

    public List<HashMap<String, String>> parse(Elements rootParsing, State rootState){
        List<HashMap<String, String>> res = new ArrayList<>();
        for (State tmpState : rootState.getChilds()) {
            res.addAll(parseElement(rootParsing, tmpState, new ArrayList<HashMap<String, String>>()));
        }
        return res;
    }

    private List<HashMap<String, String>> parseElement(Elements rootParsing, State currentState, List<HashMap<String, String>> oldHashMap){
        Elements tmpElements = findElements(rootParsing, currentState);
        for (State tmpState : currentState.getChilds()){
            tmpElements = findElements(tmpElements, tmpState);
            List<HashMap<String, String>> tmpValues = findVariables(tmpElements, tmpState);
            oldHashMap = updateHashMapValues(tmpValues, oldHashMap);
            oldHashMap = parseElement(tmpElements, tmpState, oldHashMap);
            rootParsing.removeAll(tmpElements);
        }
        return oldHashMap;
    }

    private List<HashMap<String, String>> updateHashMapValues(List<HashMap<String, String>> oldHashMap, List<HashMap<String, String>> newHachMap){
        Integer size = newHachMap.size();
        if (size > 0){
            for (int i = 0; i < size; i++){
                for (String key : newHachMap.get(i).keySet()){
                    try {
                        oldHashMap.get(i).put(key, newHachMap.get(i).get(key));
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        oldHashMap.add(newHachMap.get(i));
                    }
                }
            }
        }
        return oldHashMap;
    }

    public List<HashMap<String, String>> findVariables(Elements elements, State state){
        List<String> variableKeys = new ArrayList<>();
        List<String> variableNames = new ArrayList<>();
        List<HashMap<String, String>> variables = new ArrayList<>();
        HashMap<String, String> attrs = state.getAttributes();
        for (String key : attrs.keySet()){
            String keyValue = attrs.get(key);
            if (actualSettings.checkVariable(keyValue)){
                variableKeys.add(key);
                variableNames.add(actualSettings.getVariableName(keyValue));
            }
        }
        for (Element tmp : elements){
            HashMap<String, String> tmpValues = new HashMap<>();
            if (actualSettings.checkVariable(state.getText())){//nail
                String key = actualSettings.getVariableName(state.getText());
                String value = tmp.ownText();
                if (value.isEmpty()){
                    continue;
                }
                tmpValues.put(key, value);
            }
            for (int i = 0; i < variableKeys.size(); i++){
                try{
                    String value = tmp.attributes().get(variableKeys.get(i));
                    if (value.isEmpty()){
                        continue;
                    }
                    tmpValues.put(variableNames.get(i), value);
                }
                catch (Exception e){}
            }
            if(!tmpValues.isEmpty()) {
                variables.add(tmpValues);
            }
        }
        return variables;
    }

    public Elements findElements(Elements start, State state){
        Elements tmpElements = start.select(state.getTag());//all attributes with key
        HashMap<String, String> attrs = state.getAttributes();
        for (String key : attrs.keySet()){
            String value = attrs.get(key);
            if (actualSettings.checkVariable(value)){
                tmpElements = tmpElements.select("[" + key + "]");//all elements with attribute [key]
                continue;
            }
            tmpElements = tmpElements.select("[" + key + "*=\"" + value + "\"]");//all elements with attribute [key] and value [value]
        }
        return tmpElements;
    }

}
