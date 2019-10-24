package abstractSyntax;

import abstractSyntax.Expression;

public class Minus implements Expression {

	private Expression expression;
	
    public Minus(Expression e) {
    	this.expression = e;
    }

    public String toString() {
        return "negative " + expression.toString();
    }
}