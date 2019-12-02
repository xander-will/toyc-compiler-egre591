package codeGen.JVM;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.regex.*;
import java.util.ArrayList;

import compilers.CodeTemplate;

import globals.TCglobals;
import codeGen.JVM.JVMRuntime;

public class JVMCodeTemplate implements CodeTemplate {

    public String assignment(String lval, String rval) {
        String s = "";
        s += rval;
        s += lval;
        return s;
    }

    public String multiassignment(String lval, String rval) {
        String s = "";
        s += rval;
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
        String l_else = "ELSE_" + TCglobals.labelCount.toString();
        String l_endif = "ENDIF_" + TCglobals.labelCount.toString();

        String s = "";
        if (els != null) {
            s += cond + "\tifeq " + l_else + "\n";
            s += stmt + "\tgoto " + l_endif + "\n";
            s += l_else + ":\n" + els + l_endif + ":\n";
        } else {
            s += cond + "\tifeq " + l_endif + "\n";
            s += stmt + l_endif + ":\n";
        }

        TCglobals.labelCount++;
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

        Pattern p = Pattern.compile("ENDIF_\\d+:\n\\.end method");
        Matcher m = p.matcher(s);

        if (m.find()) {
            int endif_num = Integer.parseInt(m.group(0).replaceAll("[^0-9]+", ""));
            s = s.replaceAll("\tgoto ENDIF_" + endif_num + "\n", "");
            s = s.replaceAll("ENDIF_\\d+:\n*\\.end method", ".end method");
        }

        p = Pattern.compile("ENDLOOP_\\d+:\n\\.end method");
        Matcher n = p.matcher(s);

        // For testing right now, change later
        if (n.find()) {
            s = s.replaceAll("\n*\\.end method", "\n\ticonst 0\n\tireturn\n.end method");
        }

        return s;
    }

    private String functionHeader(Integer stack, Integer locals) {
        String s = "\t.limit stack " + stack.toString() + "\n";
        s += "\t.limit locals " + locals.toString() + "\n";
        return s;
    }

    public String globalload(String name) {
        return "\tgetstatic " + TCglobals.outputClassFileName + "/" + name + " I\n";
    }

    public String globalstore(String name) {
        return "\tputstatic " + TCglobals.outputClassFileName + "/" + name + " I\n";
    }

    public String init() {
        String s = directive("source", TCglobals.inputFileName);
        s += directive("class", "public " + TCglobals.outputClassFileName);
        s += directive("super", "java/lang/Object") + "\n";
        s += initglobals();
        s += ".method public <init>()V\n";
        s += functionHeader(1, 1);
        s += "\taload_0\n";
        s += "\tinvokespecial java/lang/Object/<init>()V\n";
        s += "\treturn\n";
        s += ".end method\n";
        return s;
    }

    public String initglobals() {
        String s = "";
        ArrayList<String> globals = TCglobals.symtable.getGlobals();

        for (String str : globals) {
            s += ".field private static " + str + " I = 0\n";
        }

        return s + "\n";
    }

    public String load(Integer id) {
        if (0 <= id && id <= 3)
            return "\tiload_" + id.toString() + "\n";
        else
            return "\tiload " + id.toString() + "\n";
    }

    public String loop(String cond, String stmt) {
        String l_loop = "LOOP_" + TCglobals.labelCount.toString();
        String l_endloop = "ENDLOOP_" + TCglobals.labelCount.toString();

        List cond_instrs = Arrays.asList(cond.split("\n"));
        Pattern p = Pattern.compile("\tistore(_|\\s)\\d+");
        Matcher m = p.matcher((String)cond_instrs.get(cond_instrs.size()-1));

        // Assignment in loop conditional, must re-load onto stack after storing
        if (m.find()) {
            int istore_num = Integer.parseInt(m.group(0).replaceAll("[^0-9]+", ""));
            cond += load(istore_num);
        }

        String s = l_loop + ":\n" + cond;
        s += "\tifeq " + l_endloop + "\n";
        s += stmt + "\tgoto " + l_loop + "\n";
        s += l_endloop + ":\n";

        TCglobals.labelCount++;
        return s;
    }

    public String main(String body, int var_num) {
        String s = functionHeader(10, var_num);
        return functionWrapper("main", "[Ljava/lang/String;", "V", s + body).replaceAll("ireturn", "return");
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
        op_table.put("||", header + "toyCOr(II)I");
        op_table.put("&&", header + "toyCAnd(II)I");
        op_table.put("<=", header + "toyCGreaterThan(II)I\n\t" + not); // not greater than
        op_table.put(">=", header + "toyCLessThan(II)I\n\t" + not); // not less than
        op_table.put("!=", header + "toyCEquals(II)I\n\t" + not);
    }

    public String operator(String op) {
        return "\t" + op_table.get(op) + "\n";
    }

    public String read() {
        return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCRead()I\n";
    }

    public String ret() {
        return "\tireturn\n";
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
            return "\tistore " + id.toString() + "\n";
    }

    public String stringLit(String s) {
        return "\tldc " + s + "\n";
    }

    public String throwaway() {
        return "\tpop\n";
    }

    public String write(String type) {
        if (type.equals("int")) {
            return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCWrite(I)V\n";
        } else {
            return "\tinvokestatic " + TCglobals.outputClassFileName + ".toyCWrite(Ljava/lang/String;)V\n";
        }
    }
}