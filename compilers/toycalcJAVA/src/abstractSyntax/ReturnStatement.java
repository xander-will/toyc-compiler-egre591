package abstractSyntax;

import abstractSyntax.Statement;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;
import output.TCoutput;

public class ReturnStatement implements Statement {

    private Expression expression;

    public ReturnStatement(Expression expr) {
        this.expression = expr;
    }

    public String generateCode() {
        String s = "";
        if (expression != null)
            s += expression.generateCode();
        else
            s += TCglobals.codetemplate.number("0");
        s += TCglobals.codetemplate.ret();

        if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
            TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

        return s;
    }

    public String toString() {
        if (expression == null)
            return "return()\n";
        String s = "return(\n";
        PrettyPrint.indent();
        s += PrettyPrint.spaces() + expression.toString();
        PrettyPrint.outdent();
        s += PrettyPrint.spaces() + ")\n";

        return s;
    }

}
