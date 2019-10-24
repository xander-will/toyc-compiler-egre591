package abstractSyntax;

import abstractSyntax.Statement;

public class IfStatement implements Statement {

	private Expression condition;
	private Statement ifs;
	private Statement els;
	
	public IfStatement(Expression condition, Statement ifs) {
		this.condition = condition;
		this.ifs = ifs;
		this.els = null;
	}

	public IfStatement(Expression condition, Statement ifs, Statement els) {
		this.condition = condition;
		this.ifs = ifs;
		this.els = els;
	}

}
