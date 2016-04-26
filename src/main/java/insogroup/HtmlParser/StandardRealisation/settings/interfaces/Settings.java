package insogroup.HtmlParser.StandardRealisation.settings.interfaces;

public interface Settings extends ProgramParameters{

    public boolean checkStardardRegexp(String text);

    public boolean checkVariable(String text);

    public String getVariableName(String text);

    public Integer getVisibleCount();

}
