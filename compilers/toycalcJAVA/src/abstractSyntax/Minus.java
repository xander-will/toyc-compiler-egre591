package abstractSyntax;

import abstractSyntax.Expression;

import globals.TCglobals;

public class Minus implements Expression {

    private Expression expression;

    public Minus(Expression e) {
        this.expression = e;
    }

    public String toString() {
        return "negative " + expression.toString();
    }

    public String generateCode() {
        return TCglobals.codetemplate.negate(expression.generateCode());
    }
}