package insogroup.HtmlParser.StandardRealisation.settings.interfaces;

public interface Settings extends ProgramParameters{

    boolean checkStandardRegexp(String text);

    boolean checkVariable(String text);

    String getVariableName(String text);

    Integer getVisibleCount();

}
