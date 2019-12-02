package compilers;

public interface CodeTemplate {

    public String assignment(String lval, String rval);

    public String multiassignment(String lval, String rval);

    public String call(String name, String args);

    public String conditional(String cond, String stmt, String els);

    public String directive(String dir, String arg);

    public String function(String name, int arg_num, String body, int var_num);

    public String globalload(String name);

    public String globalstore(String name);

    public String init();

    public String initglobals();

    public String load(Integer id);

    public String loop(String cond, String stmt);

    public String main(String body, int var_num);

    public String negate(String stmt);

    public String number(String num);

    public String operator(String op);

    public String read();

    public String ret();

    public String runtime();

    public String store(Integer id);

    public String stringLit(String s);

    public String throwaway();

    public String write(String type);
}