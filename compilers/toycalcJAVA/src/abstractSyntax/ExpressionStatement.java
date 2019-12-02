package abstractSyntax;

import abstractSyntax.Statement;
import abstractSyntax.FunctionCall;
import abstractSyntax.AssignExpression;

import globals.TCglobals;

public class ExpressionStatement implements Statement {

	private Expression expression;

	public ExpressionStatement(Expression expr) {
		this.expression = expr;
	}

	public String generateCode() {
		String s = expression.generateCode();
		if (expression instanceof FunctionCall)
			s += TCglobals.codetemplate.throwaway();
		else if (!(expression instanceof AssignExpression))
			return "";
		return s;
	}

	public String toString() {
		return expression.toString();
	}

}
