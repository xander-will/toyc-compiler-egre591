package abstractSyntax;

import abstractSyntax.Statement;
import globals.TCglobals;
import abstractSyntax.PrettyPrint;

import output.TCoutput;

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

		if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
			TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

		return s;
	}

	public String generateCode() {
		return TCglobals.codetemplate.loop(condition.generateCode(), statement.generateCode());
	}
}
