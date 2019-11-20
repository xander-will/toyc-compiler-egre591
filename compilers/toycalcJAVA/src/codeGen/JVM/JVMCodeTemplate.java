package codeGen.JVM;

import java.util.HashMap;

import compilers.CodeTemplate;

import globals.TCglobals;

public class JVMCodeTemplate implements CodeTemplate {

    private static final String runtimeFunctions = new JVMRuntime().getRuntimeFunctions();

    public String assignment(String lval, String rval) {
        String s = rval;
        s += "\tdup\n";
        s += lval;
        return s;
    }

    public String directive(String dir, String arg) {
        return "." + dir + " " + arg + "\n";
    }

    public String function(String name, String args, String body) {
        String fh = functionHeader(10, 10); // hardcoded for convenience (toyCalcdoes this as well)
        return functionWrapper(name, args, fh + body);
    }

    private String functionWrapper(String name, String args, String body) {
        String s = ".method public static " + name + "(" + args + ")V\n";
        s += body;
        s += ".end method\n";
        return s;
    }

    private String functionHeader(Integer stack, Integer locals) {
        String s = "\t.limit stack " + stack.toString() + "\n";
        s += "\t.limit locals " + locals.toString() + "\n";
        return s;
    }

    public String getRuntimeFunctions() {
        return runtimeFunctions;
    }

    public String init() {
        String s = directive("source", TCglobals.inputFileName);
        s += directive("class", "public " + TCglobals.outputClassFileName);
        s += directive("super", "java/lang/Object") + "\n";
        s += ".method public <init>()V\n";
        s += functionHeader(1, 1);
        s += "\taload_0\n";
        s += "\tinvokespecial java/lang/Object/<init>()V\n";
        s += "\treturn\n";
        s += ".end method\n";
        return s;
    }

    public String loadVar(Integer id) {
        if (0 <= id && id <= 3)
            return "\tiload_" + id.toString() + "\n";
        else
            return "iload " + id.toString() + "\n";
    }

    public String number(String num) {
        Integer x = Integer.parseInt(num);
        if (0 <= x && x <= 5)
            return "\ticonst_" + num + "\n";
        else
            return "\tldc " + num + "\n";
    }

    public String operator(String op) {
        if (op_table.containsKey(op))
            return "\t" + op_table.get(op) + "\n";

        String s = "";

        switch (op) {
        case "<=":
            s = "\tinvokestatic " + TCglobals.outputClassFileName + "." + relop_table.get("<") + "\n";
            s += "\tinvokestatic " + TCglobals.outputClassFileName + "." + relop_table.get("!") + "\n";
            return s;
        case ">=":
            s = "\tinvokestatic " + TCglobals.outputClassFileName + "." + relop_table.get(">") + "\n";
            s += "\tinvokestatic " + TCglobals.outputClassFileName + "." + relop_table.get("!") + "\n";
            return s;
        case "!=":
            s = "\tinvokestatic " + TCglobals.outputClassFileName + "." + relop_table.get("==") + "\n";
            s += "\tinvokestatic " + TCglobals.outputClassFileName + "." + relop_table.get("!") + "\n";
            return s;
        default:
            return "\tinvokestatic " + TCglobals.outputClassFileName + "." + relop_table.get(op) + "\n";
        }
    }

    private static HashMap<String, String> op_table;
    static {
        op_table = new HashMap<>();
        op_table.put("+", "iadd");
        op_table.put("-", "isub");
        op_table.put("*", "imul");
        op_table.put("/", "idiv");
    }

    public String read() {
        return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCRead()I\n";
    }

    private static HashMap<String, String> relop_table;
    static {
        relop_table = new HashMap<>();
        relop_table.put("<", "toyCLessThan(II)I");
        relop_table.put(">", "toyCGreaterThan(II)I");
        relop_table.put("==", "toyCEquals(II)I");
        relop_table.put("!", "toyCNot(I)I");
        relop_table.put("||", "toyCOr(II)I");
        relop_table.put("&&", "toyCAnd(II)I");
    }

    public String returnString() {
        return "\treturn\n";
    }

    public String storeVar(Integer id) {
        if (0 <= id && id <= 3)
            return "\tistore_" + id.toString() + "\n";
        else
            return "istore " + id.toString() + "\n";
    }

    public String write(String type) {
        if (type.equals("int")) {
            return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCWrite(I)V\n";
        } else {
            return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCWrite(Ljava/lang/String;)V\n";
        }
    }

}