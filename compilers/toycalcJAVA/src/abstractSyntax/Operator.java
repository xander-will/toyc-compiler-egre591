package abstractSyntax;

import codeGen.JVM.JVMCodeTemplate;
import globals.TCglobals;
import output.TCoutput;

public class Operator {

    private String operator;

    public Operator(String op) {
        this.operator = op;
    }

    public String generateCode() {

        String s = TCglobals.codetemplate.operator(this.operator);

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
            TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

        return s;
    }

    public String toString() {
        return "operator(" + operator + ")\n";
    }

}