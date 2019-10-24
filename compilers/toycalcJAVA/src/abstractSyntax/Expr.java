package abstractSyntax;

import abstractSyntax.Expression;

public class Expr implements Expression {

	private Operator operator;
	private Expression left;
	private Expression right;
	
	public Expr(Operator op, Expression left, Expression right) {
		this.operator = op;
		this.left = left;
		this.right = right;
	}

}
