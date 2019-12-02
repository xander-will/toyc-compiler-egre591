package abstractSyntax;

import abstractSyntax.Statement;
import abstractSyntax.ReturnStatement;
import abstractSyntax.CompoundStatement;
import abstractSyntax.IfStatement;
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

	public boolean checkReturns() {
		if (statement instanceof ReturnStatement)
				return true;
		else if (statement instanceof IfStatement)
			if (((IfStatement)statement).checkReturns())
				return true;
		else if (statement instanceof WhileStatement)
			if (((WhileStatement)statement).checkReturns())
				return true;
		else if (statement instanceof CompoundStatement)
			if (((CompoundStatement)statement).checkReturns())
				return true; 
			
		return false;
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
