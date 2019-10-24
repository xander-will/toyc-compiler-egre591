package abstractSyntax;

import abstractSyntax.Statement;

public class WhileStatement implements Statement {

	private Expression condition;
	private Statement statement;
	
	public WhileStatement(Expression condition, Statement s) {
		this.condition = condition;
		this.statement = statement;
	}

}
