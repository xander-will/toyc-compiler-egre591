package abstractSyntax;

import abstractSyntax.Expression;

import globals.TCglobals;
import output.TCoutput;

public class Number implements Expression {

    private String number;

    public Number(String num) {
        this.number = num;
    }

    public String generateCode() {

        String s = TCglobals.codetemplate.number(this.number);

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
            TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

        return s;
    }

    public String toString() {
        return "num(" + number + ")\n";
    }

}