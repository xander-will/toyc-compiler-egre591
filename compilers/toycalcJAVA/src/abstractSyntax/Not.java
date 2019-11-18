package abstractSyntax;

import abstractSyntax.Expression;
import globals.TCglobals;

public class Not implements Expression {

    private Expression expression;

    public Not(Expression e) {
        this.expression = e;
    }

    public String toString() {
        return "not " + expression.toString();
    }

    public String generateCode() {
        return "";
    }

}