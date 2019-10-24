package abstractSyntax;

import abstractSyntax.Statement;

public class ReturnStatement implements Statement {

	private Expression expression;
	
    public ReturnStatement(Expression expr) {
        this.expression = expr;
    }

}
