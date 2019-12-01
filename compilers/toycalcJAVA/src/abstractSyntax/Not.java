package abstractSyntax;

import abstractSyntax.Expression;

import globals.TCglobals;
import output.TCoutput;

public class Not implements Expression {

    private Expression expression;

    public Not(Expression e) {
        this.expression = e;
    }

    public String toString() {
        return "not " + expression.toString();
    }

    public String generateCode() {
        String s = expression.generateCode();

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
            TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN",
                    "\n" + s + TCglobals.codetemplate.operator("!"));

        return s + TCglobals.codetemplate.operator("!");
    }

}