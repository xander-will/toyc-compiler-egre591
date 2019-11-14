package abstractSyntax;

import abstractSyntax.Statement;
import abstractSyntax.PrettyPrint;

import globals.TCglobals;

public class ReturnStatement implements Statement {

	private Expression expression;
	
    public ReturnStatement(Expression expr) {
        this.expression = expr;
    }

    public String generateCode() {
        String s = expression.generateCode();
        s += TCglobals.codetemplate.return();
        return s;
    }

    public String toString() {
        if (expression == null)
            return "return()\n";
        String s = "return(\n";
        PrettyPrint.indent();
        s += PrettyPrint.spaces() + expression.toString() + "\n";
        PrettyPrint.outdent();
        s += PrettyPrint.spaces() + ")\n";

        return s;
    }

}
