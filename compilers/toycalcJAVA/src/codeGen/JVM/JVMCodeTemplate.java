package codeGen.JVM;

import java.util.HashMap;

import compilers.CodeTemplate;

import globals.TCglobals;

import codeGen.JVM.JVMRuntime;

public class JVMCodeTemplate implements CodeTemplate {

    public String assignment(String lval, String rval) {
        String s = rval;
        s += "\tdup\n";
        s += lval;
        return s;
    }

    public String call(String name, String args) {
        String header = "\tinvokestatic " + TCglobals.outputClassFileName + ".";
        String s = args + header + name + "(";
        for (int i = 0; i < TCglobals.symtable.get(name).getArgNum(); i++)
            s += "I";
        s += ")I\n";

        return s;
    }

    public String conditional(String cond, String stmt, String els) {
        String l_else = "ELSE_" + TCglobals.labelCount.toString() + "\n";
        String l_endif = "ENDIF_" + TCglobals.labelCount.toString() + "\n";

        String s = "";
        if (els != null) {
            s += cond + "\tifeq " + l_else;
            s += stmt + "\tgoto " + l_endif;
            s += l_else + els + l_endif;
        } else {
            s += "\tifeq " + l_endif;
            s += stmt + l_endif;
        }
        return s;
    }

    public String directive(String dir, String arg) {
        return "." + dir + " " + arg + "\n";
    }

    public String function(String name, int arg_num, String body, int var_num) {
        String args = "";
        for (int i = 0; i < arg_num; i++)
            args += "I";

        String fh = functionHeader(10, var_num); // hardcoded for convenience (toyCalc does this as well)
        return functionWrapper(name, args, "I", fh + body);
    }

    private String functionWrapper(String name, String args, String ret, String body) {
        String s = ".method public static " + name + "(" + args + ")" + ret + "\n";
        s += body;
        s += ".end method\n";
        return s;
    }

    private String functionHeader(Integer stack, Integer locals) {
        String s = "\t.limit stack " + stack.toString() + "\n";
        s += "\t.limit locals " + locals.toString() + "\n";
        return s;
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

    public String load(Integer id) {
        if (0 <= id && id <= 3)
            return "\tiload_" + id.toString() + "\n";
        else
            return "iload " + id.toString() + "\n";
    }

    public String loop(String cond, String stmt) {
        String l_loop = "LOOP_" + TCglobals.labelCount.toString() + "\n";
        String l_endloop = "ENDLOOP_" + TCglobals.labelCount.toString() + "\n";

        String s = l_loop + cond;
        s += "\tifeq " + l_endloop;
        s += stmt + "\tgoto " + l_loop;
        s += l_endloop;

        return s;
    }

    public String main(String body, int var_num) {
        String s = functionHeader(10, var_num);
        return functionWrapper("main", "[java/lang/String;", "V", s + body);
    }

    public String negate(String stmt) {
        return stmt + "\tineg\n";
    }

    public String number(String num) {
        Integer x = Integer.parseInt(num);
        if (0 <= x && x <= 5)
            return "\ticonst_" + num + "\n";
        else
            return "\tldc " + num + "\n";
    }

    private static HashMap<String, String> op_table;
    static {
        op_table = new HashMap<>();
        op_table.put("+", "iadd");
        op_table.put("-", "isub");
        op_table.put("*", "imul");
        op_table.put("/", "idiv");

        // these call from the runtime
        String header = "invokestatic " + TCglobals.outputClassFileName + ".";
        String not = header + "toyCNot(I)I";
        op_table.put("<", header + "toyCLessThan(II)I");
        op_table.put(">", header + "toyCGreaterThan(II)I");
        op_table.put("==", header + "toyCEquals(II)I");
        op_table.put("!", not);
        op_table.put("||", "toyCOr(II)I");
        op_table.put("&&", "toyCAnd(II)I");
        op_table.put("<=", header + "toyCGreaterThan(II)I" + not); // not greater than
        op_table.put(">=", header + "toyCLessThan(II)I" + not); // not less than
        op_table.put("!=", header + "toyCEquals(II)I" + not);
    }

    public String operator(String op) {
        return "\t" + op_table.get(op) + "\n";
    }

    public String read() {
        return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCRead()I\n";
    }

    public String ret() {
        return "\treturn\n";
    }

    public String runtime() {
        String s = "; ==============================================\n"
                + "; =========== Begin Runtime Functions ==========\n"
                + "; ==============================================\n\n";
        s += JVMRuntime.runtimeFunctions;
        s += "; ==============================================\n" + "; ============ End Runtime Functions ===========\n"
                + "; ==============================================\n\n";
        return s;
    }

    public String store(Integer id) {
        if (0 <= id && id <= 3)
            return "\tistore_" + id.toString() + "\n";
        else
            return "istore " + id.toString() + "\n";
    }

    public String stringLit(String s) {
        return "\tldc \"" + s + "\"\n";
    }

    public String write(String type) {
        if (type.equals("int")) {
            return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCWrite(I)V\n";
        } else {
            return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCWrite(Ljava/lang/String;)V\n";
        }
    }
}