package abstractSyntax;

import abstractSyntax.Expression;

public class Not implements Expression {

	private Expression expression;
	
    public Not(Expression e) {
    	this.expression = e;
    }
}