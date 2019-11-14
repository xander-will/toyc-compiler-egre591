package compilers;

public interface CodeTemplate {

    public String directive(String dir, String arg);

    public String init();

    public String loadVar(Integer id);

    public String number(String num);

    public String operator(String op);

    public String returnString();

    public String storeVar(int id);
}