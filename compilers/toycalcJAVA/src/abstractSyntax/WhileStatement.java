package abstractSyntax;

import abstractSyntax.Statement;
import abstractSyntax.PrettyPrint;

public class WhileStatement implements Statement {

	private Expression condition;
	private Statement statement;

	public WhileStatement(Expression condition, Statement s) {
		this.condition = condition;
		this.statement = s;
	}

	public String toString() {
		String s = "while(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "cond = " + condition.toString();
		s += PrettyPrint.spaces() + "statement = " + statement.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

	public String generateCode() {
		return "";
	}
}
