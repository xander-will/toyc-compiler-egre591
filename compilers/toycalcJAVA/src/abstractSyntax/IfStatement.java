package abstractSyntax;

import abstractSyntax.Statement;
import globals.TCglobals;
import abstractSyntax.PrettyPrint;
import output.TCoutput;

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

	public String toString() {
		String s = "if(\n";
		PrettyPrint.indent();
		s += PrettyPrint.spaces() + "cond = " + condition.toString();
		s += PrettyPrint.spaces() + "then = " + ifs.toString();
		if (els != null)
			s += PrettyPrint.spaces() + "else = " + els.toString();
		PrettyPrint.outdent();
		s += PrettyPrint.spaces() + ")\n";

		return s;
	}

	public String generateCode() {
		String s = "";

		if (els != null)
			s += TCglobals.codetemplate.conditional(condition.generateCode(), ifs.generateCode(), els.generateCode());
		else
			s += TCglobals.codetemplate.conditional(condition.generateCode(), ifs.generateCode(), null);

		if (TCglobals.verbose || TCglobals.debug == 0 || TCglobals.debug == 3)
			TCoutput.reportDEBUG(this.getClass().getSimpleName(), "CODEGEN", "\n" + s);

		return s;
	}
}
