package abstractSyntax;

import abstractSyntax.Statement;

public class ExpressionStatement implements Statement {

	private Expression expression;

	public ExpressionStatement(Expression expr) {
		this.expression = expr;
	}

	public String generateCode() {
		return expression.generateCode();
	}

	public String toString() {
		return expression.toString();
	}

}
