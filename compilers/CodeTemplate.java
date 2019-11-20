package compilers;

public interface CodeTemplate {

    public String assignment(String lval, String rval);

    public String directive(String dir, String arg);

    public String function(String name, String args, String body);

    public String init();

    public String getRuntimeFunctions();

    public String loadVar(Integer id);

    public String number(String num);

    public String operator(String op);

    public String read();

    public String returnString();

    public String storeVar(Integer id);

    public String write(String type);
}